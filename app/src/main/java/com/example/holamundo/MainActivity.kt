package com.example.holamundo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.spoonacular.com/recipes/"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getMyData()
    }

    private fun getMyData()
    {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(APIInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<RecipesItem>?> {
            override fun onResponse(
                call: Call<List<RecipesItem>?>,
                response: Response<List<RecipesItem>?>
            ) {
                val responseBody = response.body()!!
                val stringBuilder = StringBuilder()

                for (myData in responseBody)
                {
                    stringBuilder.append(myData.title)
                    stringBuilder.append("\n")
                }

                val textView: TextView = findViewById(R.id.txtID) as TextView
                textView.text = stringBuilder
            }

            override fun onFailure(call: Call<List<RecipesItem>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }
}