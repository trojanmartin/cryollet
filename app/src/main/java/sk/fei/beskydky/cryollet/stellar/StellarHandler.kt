package sk.fei.beskydky.cryollet.stellar

import android.util.Log
import kotlinx.coroutines.*
import org.stellar.sdk.*
import org.stellar.sdk.requests.ErrorResponse
import org.stellar.sdk.responses.AccountResponse
import org.stellar.sdk.responses.SubmitTransactionResponse
import org.stellar.sdk.responses.operations.OperationResponse
import org.stellar.sdk.xdr.AssetType
import org.stellar.sdk.xdr.ClaimClaimableBalanceOp
import java.io.InputStream
import java.net.URL
import java.util.*
import kotlin.Exception


class StellarHandler(
    var server: Server = Server("https://horizon-testnet.stellar.org"),
    var network: Network = Network.TESTNET
) {

    suspend fun createAccount(newAccount: KeyPair = KeyPair.random()): KeyPair = withContext(Dispatchers.IO) {
        launch {
            val friendBotUrl = java.lang.String.format(
                "https://friendbot.stellar.org/?addr=%s",
                newAccount.getAccountId()
            )
            val response: InputStream = URL(friendBotUrl).openStream()
            val body: String = Scanner(response, "UTF-8").useDelimiter("\\A").next()

            val account: AccountResponse = server.accounts().account(newAccount.getAccountId())
            Log.i("Stellar", account.getAccountId())
        }

        return@withContext newAccount
    }

    suspend fun getBalances(keyPair: KeyPair): Array<AccountResponse.Balance>? = withContext(Dispatchers.IO) {
        var sourceAccount: AccountResponse? = null
        sourceAccount = server.accounts().account(keyPair.getAccountId())

        return@withContext sourceAccount?.balances
    }

    suspend fun sendTransaction(source: KeyPair, destinationId: String,
                                assetType: AssetTypeNative = AssetTypeNative(),
                                value: String, memo: String = "testTransaction"):
                                SubmitTransactionResponse? = withContext(Dispatchers.IO) {

        var transactionResponse: SubmitTransactionResponse? = null
        val sourceAccount: AccountResponse = server.accounts().account(source.accountId)
        val destination = KeyPair.fromAccountId(destinationId)

        val assetResponse = server.assets().assetCode("EUR").assetIssuer("GAKNDFRRWA3RPWNLTI3G4EBSD3RGNZZOY5WKWYMQ6CQTG3KIEKPYWAYC").execute()
        val asset = assetResponse.records[0].asset

        val transaction: Transaction = Transaction.Builder(sourceAccount, network)
            .addOperation(PaymentOperation.Builder(destination.accountId, asset, value).build())
            .addMemo(Memo.text(memo))
            // Wait max 3 minutes
            .setTimeout(180L)
            .setBaseFee(Transaction.MIN_BASE_FEE)
            .build()

        transaction.sign(source)

        try {
            transactionResponse = server.submitTransaction(transaction)
            Log.i("Stellar", transactionResponse.toString())
        } catch (e: Exception) {
            Log.e("Stellar", e.message.toString())
        }

        return@withContext transactionResponse
    }

    suspend fun getPayments(source: KeyPair) = withContext(Dispatchers.IO) {
        var list: ArrayList<OperationResponse>? = null
        val paymentsOperationResponse = server.payments().forAccount(source.accountId).limit(100).execute()


        list = paymentsOperationResponse.records
        Log.i("Stellar", list.toString())
        return@withContext list
    }

    suspend fun getAccount(secretSeed: String): KeyPair? = withContext(Dispatchers.IO) {
        val keys = KeyPair.fromSecretSeed(secretSeed)
        try {
            val account: AccountResponse = server.accounts().account(keys.accountId)
        } catch (e :ErrorResponse) {
            Log.e("Stellar", e.message.toString())
            if (e.code == 404) return@withContext null
        }

        return@withContext keys
    }

    private suspend fun getAsset(code: String) = withContext(Dispatchers.IO) {

    }

}