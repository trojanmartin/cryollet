package sk.fei.beskydky.cryollet.stellar

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import org.stellar.sdk.*
import org.stellar.sdk.requests.ErrorResponse
import org.stellar.sdk.responses.AccountResponse
import org.stellar.sdk.responses.AssetResponse
import org.stellar.sdk.responses.SubmitTransactionResponse
import org.stellar.sdk.responses.operations.OperationResponse
import java.net.URL
import java.util.*
import kotlin.Exception


class StellarHandler(
    var server: Server = Server("https://horizon-testnet.stellar.org"),
    var network: Network = Network.TESTNET,
    var issuer: KeyPair = KeyPair.fromSecretSeed("SDA5JK3V55GXYBC74Z2QHYBC2DTRA4UN7C5APPKJWLUK3ETBGF5RKJBZ")
) {

    suspend fun createAccount(newAccount: KeyPair = KeyPair.random()): KeyPair = withContext(Dispatchers.IO) {
        val friendBotUrl = java.lang.String.format(
            "https://friendbot.stellar.org/?addr=%s",
            newAccount.getAccountId()
        )
        URL(friendBotUrl).openStream()

        val assetResponse = server.assets().assetIssuer(issuer.accountId).execute()

        // Create trust line with assets and fund new account
        createTrustLineWithAssets(newAccount, assetResponse.records)
        fundAccount(newAccount, assetResponse.records)

        return@withContext newAccount
    }

    suspend fun getBalances(keyPair: KeyPair): Array<AccountResponse.Balance>? = withContext(Dispatchers.IO) {
        val sourceAccount = server.accounts().account(keyPair.getAccountId())
        return@withContext sourceAccount?.balances
    }

    suspend fun sendTransaction(source: KeyPair, destinationId: String,
                                assetCode: String = "XLM",
                                value: String, memo: String = "testTransaction"):
                                SubmitTransactionResponse? = withContext(Dispatchers.IO) {

        val sourceAccount: AccountResponse = server.accounts().account(source.accountId)
        val destination = KeyPair.fromAccountId(destinationId)

        val asset = if (assetCode!=="XLM") getAsset(assetCode) else AssetTypeNative()

        val transaction: Transaction = Transaction.Builder(sourceAccount, network)
            .addOperation(PaymentOperation.Builder(destination.accountId, asset, value).build())
            .addMemo(Memo.text(memo))
            // Wait max 3 minutes
            .setTimeout(180L)
            .setBaseFee(Transaction.MIN_BASE_FEE)
            .build()

        return@withContext submitTransaction(transaction, source)
    }

    suspend fun getPayments(source: KeyPair) = withContext(Dispatchers.IO) {
        var list: ArrayList<OperationResponse>? = null
        val paymentsOperationResponse = server.payments().forAccount(source.accountId).limit(100).execute()

        list = paymentsOperationResponse.records
        return@withContext list
    }

    suspend fun getAccount(secretSeed: String): KeyPair? = withContext(Dispatchers.IO) {
        val accKeys = KeyPair.fromSecretSeed(secretSeed)
        try {
            server.accounts().account(accKeys.accountId)
        } catch (e :ErrorResponse) {
            Log.e("Stellar", e.message.toString())
            if (e.code == 404) return@withContext null
        }
        val assetResponse = server.assets().assetIssuer(issuer.accountId).execute()

        // Create trust line with assets and fund new account
        createTrustLineWithAssets(accKeys, assetResponse.records)
        fundAccount(accKeys, assetResponse.records)

        return@withContext accKeys
    }

    private suspend fun submitTransaction(transaction: Transaction, signer: KeyPair) = withContext(Dispatchers.IO){
        transaction.sign(signer)

        var transactionResponse: SubmitTransactionResponse? = null
        try {
            transactionResponse = server.submitTransaction(transaction)
        } catch (e: Exception) {
            Log.e("Stellar", e.message.toString())
        }
        return@withContext transactionResponse
    }

    private suspend fun getAsset(code: String): Asset = withContext(Dispatchers.IO) {
        val assetResponse = server.assets()
            .assetCode(code)
            .assetIssuer(issuer.accountId)
            .execute()
        return@withContext assetResponse.records[0].asset
    }

    private suspend fun createTrustLineWithAssets(accKeys: KeyPair, assets: ArrayList<AssetResponse>) = withContext(Dispatchers.IO) {
        val account: AccountResponse = server.accounts().account(accKeys.getAccountId())
        val changeTrustTransaction = Transaction.Builder(account, network)
            .addOperation(ChangeTrustOperation.Builder(ChangeTrustAsset.create(assets[0].asset), "2147483647").build())
            .addOperation(ChangeTrustOperation.Builder(ChangeTrustAsset.create(assets[1].asset), "2147483647").build())
            .addOperation(ChangeTrustOperation.Builder(ChangeTrustAsset.create(assets[2].asset), "2147483647").build())
            .setTimeout(180L)
            .setBaseFee(Transaction.MIN_BASE_FEE)
            .build()
        submitTransaction(changeTrustTransaction, accKeys)
    }

    private suspend fun fundAccount(accKeys: KeyPair, assets: ArrayList<AssetResponse>) = withContext(Dispatchers.IO) {
        val issuerAccount: AccountResponse = server.accounts().account(issuer.getAccountId())
        val fundNewAccountTransaction = Transaction.Builder(issuerAccount, network)
            .addOperation(PaymentOperation.Builder(accKeys.accountId, assets[0].asset, "30").build())
            .addOperation(PaymentOperation.Builder(accKeys.accountId, assets[1].asset, "15").build())
            .addOperation(PaymentOperation.Builder(accKeys.accountId, assets[2].asset, "10").build())
            .setTimeout(180L)
            .setBaseFee(Transaction.MIN_BASE_FEE)
            .build()
        submitTransaction(fundNewAccountTransaction, issuer)
    }

    companion object {

        @Volatile
        private var INSTANCE: StellarHandler? = null

        fun getInstance(context: Context): StellarHandler {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = StellarHandler()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}