package com.mandrykevich.testapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import api.api.RetrofitClient
import com.mandrykevich.testapp.R
import com.mandrykevich.testapp.databinding.ActivityMainBinding
import room.AppDatabase
import room.JobRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val apiService = RetrofitClient.getApiService()

    private val jobViewModel: JobViewModel by viewModels {
        JobViewModelFactory(JobRepository(AppDatabase.getDatabase(applicationContext).jobDao(), apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.navigation_frame, MainFragment())
                .commit()
        }
        jobViewModel.refreshOffersAndVacancies()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val fragment = when(item.itemId) {
                R.id.item_search -> MainFragment()
                R.id.item_favorite -> FavoriteFragment()
                R.id.item_feedback -> EmptyFragment()
                R.id.item_massenger -> EmptyFragment()
                R.id.item_profile -> EmptyFragment()
                else -> null
            }

            fragment?.let {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.navigation_frame)
                if (currentFragment == null || !currentFragment::class.java.isInstance(fragment)) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.navigation_frame, fragment)
                        .commit()
                }
            }
            true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}