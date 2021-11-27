package sk.fei.beskydky.cryollet.database.stellar;


import java.net.*;
import java.io.*;
import java.util.*;

import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;
import org.stellar.sdk.Network;
import org.stellar.sdk.PaymentOperation;
import org.stellar.sdk.Server;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;

public final class StellarHandler {

    private static String PUBLIC_KEY = "GBOIRKCFJVMJGTUM2F2U4ITMGEP6WYUJ4F7ADPQC5H7A4GGBSNXNUD3L";
    private static String PRIVATE_KEY = "SDLHL276RFFPFFUUSBPX6SRTHPKSFZHBF4KCVX2GODXVJNXRUF2JBZNX";

    public static KeyPair createWallet() throws IOException {
        KeyPair pair = KeyPair.random();

        String friendbotUrl = String.format(
                "https://friendbot.stellar.org/?addr=%s",
                pair.getAccountId());

        InputStream response = new URL(friendbotUrl).openStream();
        String body = new Scanner(response, "UTF-8").useDelimiter("\\A").next();

        Server server = new Server("https://horizon-testnet.stellar.org");
        AccountResponse account = server.accounts().account(pair.getAccountId());
        return pair;
    }

    public static void sendPayment() throws IOException {
        Server server = new Server("https://horizon-testnet.stellar.org");

        KeyPair source = KeyPair.fromSecretSeed(PRIVATE_KEY);
        KeyPair destination = KeyPair.fromAccountId("GA2C5RFPE6GCKMY3US5PAB6UZLKIGSPIUKSLRB6Q723BM2OARMDUYEJ5");

        // First, check to make sure that the destination account exists.
        // You could skip this, but if the account does not exist, you will be charged
        // the transaction fee when the transaction fails.
        // It will throw HttpResponseException if account does not exist or there was another error.
        server.accounts().account(destination.getAccountId());

        // If there was no error, load up-to-date information on your account.
        AccountResponse sourceAccount = server.accounts().account(source.getAccountId());

        // Start building the transaction.
        Transaction transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
                .addOperation(new PaymentOperation.Builder(destination.getAccountId(), new AssetTypeNative(), "10").build())
                // A memo allows you to add your own metadata to a transaction. It's
                // optional and does not affect how Stellar treats the transaction.
                .addMemo(Memo.text("Test Transaction"))
                // Wait a maximum of three minutes for the transaction
                .setTimeout(180)
                // Set the amount of lumens you're willing to pay per operation to submit your transaction
                .setBaseFee(Transaction.MIN_BASE_FEE)
                .build();
        // Sign the transaction to prove you are actually the person sending it.
        transaction.sign(source);

        // And finally, send it off to Stellar!
        try {
            SubmitTransactionResponse response = server.submitTransaction(transaction);
            System.out.println("Success!");
            System.out.println(response);
        } catch (Exception e) {
            System.out.println("Something went wrong!");
            System.out.println(e.getMessage());
            // If the result is unknown (no response body, timeout etc.) we simply resubmit
            // already built transaction:
            // SubmitTransactionResponse response = server.submitTransaction(transaction);
        }
    }

}
