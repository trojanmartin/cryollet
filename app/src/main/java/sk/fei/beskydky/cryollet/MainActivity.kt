package sk.fei.beskydky.cryollet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2

import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabase
import sk.fei.beskydky.cryollet.database.repository.BalanceRepository
import sk.fei.beskydky.cryollet.database.repository.TransactionRepository
import sk.fei.beskydky.cryollet.stellar.StellarHandler


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            refreshDatabase()
        }
        setContentView(R.layout.activity_main)
        val menu = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.fragmentContainerView)
        menu.setupWithNavController(navController)
    }

    private suspend fun refreshDatabase(){
        val dataSource = AppDatabase.getInstance(application).appDatabaseDao
        val stellarDataSource = StellarHandler.getInstance(application)
        try {
            TransactionRepository.getInstance(dataSource, stellarDataSource).refreshDatabaseFromStellar()
            BalanceRepository.getInstance(dataSource, stellarDataSource).fillWithData()
        } catch (e: Exception){
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
        }
    }
}