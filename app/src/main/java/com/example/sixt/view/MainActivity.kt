package com.example.sixt.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.sixt.R
import com.example.sixt.databinding.ActivityMainBinding
import com.example.sixt.viewmodel.CarViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var _binding: ActivityMainBinding
    private lateinit var carViewModel: CarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        // Inflate the layout for this Activity
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setSupportActionBar(_binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        carViewModel = ViewModelProvider(this)[CarViewModel::class.java]
        carViewModel.getCarList()
        observeViewModel()
        _binding.contentMain.bottomNavBar.setupWithNavController(navController)

    }

    private fun observeViewModel(){
        carViewModel.toastMessageObservable.observe(this){
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}