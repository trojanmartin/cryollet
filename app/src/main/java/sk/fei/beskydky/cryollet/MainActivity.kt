package sk.fei.beskydky.cryollet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2

import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MenuViewModel
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val menu = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        viewModel = ViewModelProvider(this)[MenuViewModel::class.java]
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = BottomMenuAdapter(this)
        viewPager.setCurrentItem(1, true)
        menu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.transactions_menu_item -> {
                    viewPager.setCurrentItem(0, true)
                }
                R.id.home_menu_item -> {
                    viewPager.setCurrentItem(1, true)
                }
                R.id.contacts_menu_item -> {
                    viewPager.setCurrentItem(2, true)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

}