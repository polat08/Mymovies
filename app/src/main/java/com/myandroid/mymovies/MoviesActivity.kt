package com.myandroid.mymovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query


@Suppress("DEPRECATION")
class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        // Choose authentication providers
        Log.d("testlogs","register")

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = GridLayoutManager(this, 2)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ItemsViewModel(com.firebase.ui.auth.R.drawable.common_full_open_on_phone, "Item " + i))
        }


        val apiInterface = ApiInterface.create().getMovies("3f5a198cdda706ff5defacb6c84f41de")

        //apiInterface.enqueue( Callback<List<Movie>>())
        apiInterface.enqueue( object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>?, response: Response<Movies>?) {
                Log.d("tessstlogs", "OnResponse Success ${call.toString()} ${response?.body()?.results}")
                // This will pass the ArrayList to our Adapter
                val adapter = CustomAdapter(response?.body()?.results)

                // Setting the Adapter with the recyclerview
                recyclerview.adapter = adapter


//                if(response?.body() != null)
//                    recyclerAdapter.setMovieListItems(response.body()!!)
            }

            override fun onFailure(call: Call<Movies>?, t: Throwable?) {
                Log.d("tessstlogs", "onFailure :${t?.message}")
            }
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }
}