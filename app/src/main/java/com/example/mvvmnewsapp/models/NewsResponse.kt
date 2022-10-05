package com.example.mvvmnewsapp.models

import com.example.mvvmnewsapp.models.Article


data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)