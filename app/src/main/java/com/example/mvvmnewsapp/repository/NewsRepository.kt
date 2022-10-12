package com.example.mvvmnewsapp.repository

import com.example.mvvmnewsapp.api.RetrofitInstance
import com.example.mvvmnewsapp.db.ArticleDatabase

// the purpose of the repository is to get the data from our database and our remote
// data source so from retrofit from the api
// so we will have a function that directly queries our API for the breaking news and in our news
//
class NewsRepository(
    val db: ArticleDatabase
    //we need to access the API but we can get that from our retrofit instance class
    //by calling api('val api by lazy') in RetrofitInstance class
    //so we don't need to pass that as parameter here
)  {
    //since network function is suspend function we also need to make this as suspend function
    //And this function has to be called from another suspend function or within coroution
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}