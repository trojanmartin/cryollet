package sk.fei.beskydky.cryollet.stellar

import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import org.stellar.sdk.*
import org.stellar.sdk.requests.ErrorResponse
import org.stellar.sdk.responses.AccountResponse
import org.stellar.sdk.responses.AssetResponse
import org.stellar.sdk.responses.Page
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
        fundWithFriendBot(newAccount.getAccountId())

        val assetResponse = getAssetsByIssuer()

        // Create trust line with assets and fund new account
        createTrustLineWithAssets(newAccount, assetResponse.records)
        fundAccount(newAccount, assetResponse.records)

        return@withContext newAccount
    }

    suspend fun getBalances(keyPair: KeyPair): Array<AccountResponse.Balance>? = withContext(Dispatchers.IO) {
        val sourceAccount = loadAccount(keyPair.accountId)
        return@withContext sourceAccount?.balances
    }

    suspend fun sendTransaction(source: KeyPair, destinationId: String,
                                assetCode: String = "XLM",
                                value: String, memo: String = "testTransaction"):
                                SubmitTransactionResponse? = withContext(Dispatchers.IO) {

        val sourceAccount: AccountResponse = loadAccount(source.accountId)
        val destination = KeyPair.fromAccountId(destinationId)

        val asset = if (assetCode != "XLM") getAsset(assetCode) else AssetTypeNative()

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
        try {
            val paymentsOperationResponse = server.payments().forAccount(source.accountId).limit(100).execute()
            list = paymentsOperationResponse.records
        } catch (e: ErrorResponse) {
            throw Exception("Loading payments failed.")
        }

        return@withContext list
    }

    suspend fun getAccount(secretSeed: String): KeyPair? = withContext(Dispatchers.IO) {
        val accKeys = try { KeyPair.fromSecretSeed(secretSeed) } catch (e: Exception) { throw Exception("Wrong secret seed!") }

        loadAccount(accKeys.accountId)

        val assetResponse = getAssetsByIssuer()

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
        } catch (e: ErrorResponse) {
            Log.e("Stellar", e.message.toString())
            throw Exception("Submitting transaction failed.")
        }
        return@withContext transactionResponse
    }

    private suspend fun getAsset(code: String): Asset = withContext(Dispatchers.IO) {
        val assetResponse: Page<AssetResponse>
        try {
            assetResponse = server.assets()
                .assetCode(code)
                .assetIssuer(issuer.accountId)
                .execute()
        } catch (e: ErrorResponse) {
            throw Exception("Loading asset failed.")
        }
        return@withContext assetResponse.records[0].asset
    }

    private suspend fun getAssetsByIssuer() = withContext(Dispatchers.IO) {
        val response: Page<AssetResponse>
        try {
            response = server.assets().assetIssuer(issuer.accountId).execute()
        } catch (e: ErrorResponse) {
            throw Exception("Loading assets failed.")
        }
        return@withContext response
    }

    private suspend fun createTrustLineWithAssets(accKeys: KeyPair, assets: ArrayList<AssetResponse>) = withContext(Dispatchers.IO) {
        val account: AccountResponse = loadAccount(accKeys.accountId)
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
        val issuerAccount: AccountResponse = loadAccount(issuer.accountId)
        val fundNewAccountTransaction = Transaction.Builder(issuerAccount, network)
            .addOperation(PaymentOperation.Builder(accKeys.accountId, assets[0].asset, "30").build())
            .addOperation(PaymentOperation.Builder(accKeys.accountId, assets[1].asset, "15").build())
            .addOperation(PaymentOperation.Builder(accKeys.accountId, assets[2].asset, "10").build())
            .setTimeout(180L)
            .setBaseFee(Transaction.MIN_BASE_FEE)
            .build()
        submitTransaction(fundNewAccountTransaction, issuer)
    }

    private suspend fun loadAccount(accountId: String) = withContext(Dispatchers.IO) {
        val account: AccountResponse
        try {
            account = server.accounts().account(accountId)
        } catch (e: ErrorResponse) {
            if (e.code == 404) throw Exception("Account does not exist.")
            throw Exception("Loading account failed.")
        }
        return@withContext account
    }

    private suspend fun fundWithFriendBot(accountId: String) = withContext((Dispatchers.IO)) {
        val friendBotUrl = java.lang.String.format(
            "https://friendbot.stellar.org/?addr=%s",
            accountId
        )
        try {
            URL(friendBotUrl).openStream()
        } catch (e: ErrorResponse) {
            throw Exception("Funding with friend bot failed.")
        }
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