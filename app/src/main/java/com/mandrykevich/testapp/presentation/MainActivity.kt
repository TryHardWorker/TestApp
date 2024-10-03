package com.mandrykevich.testapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import api.api.RetrofitClient
import com.mandrykevich.testapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import room.AppDatabase
import room.JobRepository
import room.OfferEntity

class MainActivity : AppCompatActivity() {

    private val jobViewModel: JobViewModel by viewModels {
        JobViewModelFactory(JobRepository(AppDatabase.getDatabase(applicationContext).jobDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.tv)
        val b = findViewById<View>(R.id.bb)

        val apiService = RetrofitClient.getApiService()
        b.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val res = apiService.getResponse()
                runOnUiThread {
                    val offers = res.offers.map { offer ->
                        OfferEntity(id = offer.id, title = offer.title, link = offer.link, buttonText = offer.button?.text)
                    }
                    jobViewModel.insertOffers(offers)

                    tv.text = res.offers.size.toString()
                }
            }
        }
    }
}