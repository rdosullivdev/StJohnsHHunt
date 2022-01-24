package com.ros.stjohnshhunt

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.ros.stjohnshhunt.databinding.ActivityMainBinding
import com.ros.stjohnshhunt.workers.SyncHousesWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        /*val request = OneTimeWorkRequestBuilder<SyncHousesWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()*/

//        val operation = WorkManager.getInstance(this)
//            .enqueueUniqueWork("MyJob", ExistingWorkPolicy.REPLACE, request)
//
//        Log.d("MainActivity", "operation $operation")
    }
}
