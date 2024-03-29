package com.myandroid.mymovies


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("3/movie/popular")
    fun getMovies(@Query("api_key") sort : String ) : Call<Movies>

    companion object {

        var BASE_URL = "https://api.themoviedb.org/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}


