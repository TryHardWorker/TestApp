package com.mandrykevich.testapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import api.api.RetrofitClient
import com.mandrykevich.testapp.R
import room.AppDatabase
import room.JobRepository

class MainActivity : AppCompatActivity() {

    private val apiService = RetrofitClient.getApiService()

    private val jobViewModel: JobViewModel by viewModels {
        JobViewModelFactory(JobRepository(AppDatabase.getDatabase(applicationContext).jobDao(), apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.navigarion_frame, MainFragment())
                .commit()
        }
        jobViewModel.dataLoaded.observe(this) { dataLoaded ->
            if (dataLoaded == true) {
                showToast("Данные успешно загружены!")
            } else {
                showToast("Ошибка загрузки данных.")
            }
        }

        jobViewModel.refreshOffersAndVacancies()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}