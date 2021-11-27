//package sk.fei.beskydky.cryollet.database.stellar;
//
//
//import java.net.*;
//import java.io.*;
//import java.util.*;
//import org.stellar.sdk.KeyPair;
//import org.stellar.sdk.Server;
//import org.stellar.sdk.responses.AccountResponse;
//
//public final class StellarHandler {
//
//    public static KeyPair createWallet() throws IOException {
//        KeyPair pair = KeyPair.random();
//
//        String friendbotUrl = String.format(
//                "https://friendbot.stellar.org/?addr=%s",
//                pair.getAccountId());
//
//        InputStream response = new URL(friendbotUrl).openStream();
//        String body = new Scanner(response, "UTF-8").useDelimiter("\\A").next();
//
//        Server server = new Server("https://horizon-testnet.stellar.org");
//        AccountResponse account = server.accounts().account(pair.getAccountId());
//        return pair;
//    }
//
//}
