package com.mandrykevich.testapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import api.ApiInterface
import api.Button
import com.mandrykevich.testapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.tv)
        val b = findViewById<View>(R.id.bb)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/u/0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val API = retrofit.create(ApiInterface::class.java)
        b.setOnClickListener { CoroutineScope(Dispatchers.IO).launch {
            val res = API.getResponse()
            runOnUiThread{
                tv.text = res.offers.size.toString()
            }
        }
        }
    }
}