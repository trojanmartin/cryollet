package sk.fei.beskydky.cryollet.ui.login

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import sk.fei.beskydky.cryollet.databinding.ActivityLoginBinding

import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.ui.login.key.KeyLoginViewModel
import sk.fei.beskydky.cryollet.ui.login.key.KeyLoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
   }
//            setResult(Activity.RESULT_OK)
//
//            //Complete and destroy login activity once successful
//            finish()

}