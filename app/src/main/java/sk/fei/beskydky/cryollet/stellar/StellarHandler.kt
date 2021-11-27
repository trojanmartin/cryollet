package sk.fei.beskydky.cryollet.stellar

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.stellar.sdk.Account
import org.stellar.sdk.KeyPair
import org.stellar.sdk.Network
import org.stellar.sdk.Server
import org.stellar.sdk.responses.AccountResponse
import java.io.InputStream
import java.net.URL
import java.util.*


class StellarHandler(
    var server: Server = Server("https://horizon-testnet.stellar.org"),
) {

    suspend fun createAccount(newAccount: KeyPair = KeyPair.random()): KeyPair {
        val friendBotUrl = java.lang.String.format(
            "https://friendbot.stellar.org/?addr=%s",
            newAccount.getAccountId()
        )
        val response: InputStream = URL(friendBotUrl).openStream()
        val body: String = Scanner(response, "UTF-8").useDelimiter("\\A").next()

        val account: AccountResponse = server.accounts().account(newAccount.getAccountId())

        return newAccount
    }

}