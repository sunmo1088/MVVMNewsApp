package com.example.mvvmnewsapp.api

import com.example.mvvmnewsapp.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitInstance {
    companion object {

        //by lazy - only initialize in once
        private val retrofit by lazy {
            // I added a dependency earlier to be able to log response of retrofit
            // which is very useful for debugging
            // this logging interceptor give ability to log response of retrofit
            // We attach to retrofit object to be able to see which requests we are
            // actually making and what the response are
            val logging = HttpLoggingInterceptor()
            // We can see body of response
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                    // It is used to determine how the we response should
                    // actually be interpreted and converted to Kotlin object
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}