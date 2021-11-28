package sk.fei.beskydky.cryollet

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import java.security.KeyFactory
import java.security.MessageDigest
import java.text.SimpleDateFormat
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


/**
 * Take the Long milliseconds returned by the system and stored in Room,
 * and convert it to a nicely formatted string for display.
 *
 * EEEE - Display the long letter version of the weekday
 * MMM - Display the letter abbreviation of the nmotny
 * dd-yyyy - day in month and full year numerically
 * HH:mm - Hours and minutes in 24hr format
 */
@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(systemTime).toString()
}

fun View.setHideKeyboardOnClick(fragment: Fragment){
    setOnClickListener{
        fragment.hideKeyboard()
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun String.aesEncrypt(key: String): String {
    val plaintext: ByteArray = this.toByteArray()

    val secretKeyEcb: SecretKey = SecretKeySpec(key.toByteArray(), "AES")
    val cipher = Cipher.getInstance("AES")
    val ivParameterSpec = IvParameterSpec("bVQzNFNhRkQ1Njc4UUFaWA".toByteArray())
    cipher.init(Cipher.ENCRYPT_MODE, secretKeyEcb,ivParameterSpec )
    return cipher.doFinal(plaintext).toString()
    //val iv: ByteArray = cipher.iv
}

fun String.aesDecrypt(key: String): String {
    val plaintext: ByteArray = this.toByteArray()

    val secretKeyEcb: SecretKey = SecretKeySpec(key.toByteArray(), "AES")
    val ivParameterSpec = IvParameterSpec("bVQzNFNhRkQ1Njc4UUFaWA".toByteArray())
    val cipher = Cipher.getInstance("AES")
    cipher.init(Cipher.DECRYPT_MODE, secretKeyEcb, ivParameterSpec)
    return cipher.doFinal(plaintext).toString()
    //val iv: ByteArray = cipher.iv
}