package com.example.holamundo

import android.annotation.SuppressLint
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
    val recipeList = mutableListOf<RecipesItem>()
    val idList = mutableListOf<Int>()
    private val retrofitBuilder: APIInterface = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(APIInterface::class.java)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getMyData(idList)
        val textView: TextView = findViewById(R.id.txtID)
        //textView.text = idList.toString()
        //for(recipe in recipeList)
        //    getMySummary(recipe.id)
        //getMySteps()
    }

    private fun getMyData(list: MutableList<Int>)
    {
        val recipeList = mutableListOf<RecipesItem>()
        retrofitBuilder.getData().enqueue(object : Callback<List<RecipesItem>?> {
            override fun onResponse(
                call: Call<List<RecipesItem>?>,
                response: Response<List<RecipesItem>?>
            ) {
                val responseBody = response.body()!!
                val stringBuilder = StringBuilder()

                for (myData in responseBody)
                {
                    //stringBuilder.append(myData is RecipesItem)
                    //stringBuilder.append(myData.title)
                    //stringBuilder.append("\n")

                    list.add(myData.id)
                }
                val textView: TextView = findViewById(R.id.txtID)
                textView.text = ""
                for (id in list)
                {
                    getMySummary(id)
                }
            }

            override fun onFailure(call: Call<List<RecipesItem>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }

    private fun getMySummary(id: Int) {
        retrofitBuilder.getSummary(id).enqueue(object : Callback<Summary?> {
            override fun onResponse(call: Call<Summary?>, response: Response<Summary?>) {
                val responseBody = response.body()!!
                val stringBuilder = StringBuilder()
                val textView: TextView = findViewById(R.id.txtID)

                stringBuilder.append(responseBody.title + "\n")
                stringBuilder.append(responseBody.summary)

                var output = stringBuilder.toString().replace("<b>", "")
                output = output.replace("</b>", "")
                output = output.replace("\\.[^\\.]+<a href=[\\S\\s]+".toRegex(), "")
                output += "."
                //stringBuilder.append("\n\n" + output + "\n")
                output = textView.text.toString() + output + "\n"
                textView.text =  output
            }

            override fun onFailure(call: Call<Summary?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }

    private fun getMySteps(){
        retrofitBuilder.getSteps().enqueue(object : Callback<List<AnalizedStepsItem>?> {
            override fun onResponse(
                call: Call<List<AnalizedStepsItem>?>,
                response: Response<List<AnalizedStepsItem>?>
            ) {
                val responseBody = response.body()!!
                val stringBuilder = StringBuilder()
                val textView: TextView = findViewById(R.id.txtID)
                val stepList = responseBody[0]
                for(steps in stepList.steps)
                {
                    stringBuilder.append(steps.step + "\n")
                }

                textView.text = stringBuilder
            }

            override fun onFailure(call: Call<List<AnalizedStepsItem>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }
}