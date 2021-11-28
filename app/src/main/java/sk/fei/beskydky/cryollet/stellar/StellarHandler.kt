package sk.fei.beskydky.cryollet.stellar

import android.util.Log
import kotlinx.coroutines.*
import org.stellar.sdk.KeyPair
import org.stellar.sdk.Network
import org.stellar.sdk.Server
import org.stellar.sdk.responses.AccountResponse
import java.io.InputStream
import java.net.URL
import java.util.*


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
        launch {
            sourceAccount = server.accounts().account(keyPair.getAccountId())
            delay(2000L)

        }
        return@withContext sourceAccount?.balances
    }



}