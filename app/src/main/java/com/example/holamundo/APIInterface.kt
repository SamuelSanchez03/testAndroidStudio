package com.example.holamundo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIInterface {
    @GET("findByIngredients?apiKey=0419d9cd6d75420aae0691315b632fb7&ingredients=apples,+flour,+sugar&number=5")
    fun getData(): Call<List<RecipesItem>>

    @GET("{id}/summary?apiKey=0419d9cd6d75420aae0691315b632fb7")
    fun getSummary(@Path("id") id:Int): Call<Summary>
    @GET("324694/analyzedInstructions?apiKey=0419d9cd6d75420aae0691315b632fb7")
    fun getSteps(): Call<List<AnalizedStepsItem>>
}