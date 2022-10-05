package com.example.mvvmnewsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity annotation tells this Article class is a table in our database
@Entity(
    tableName = "articles"
)
data class Article(
    //set id nullable because not every article need id.
    //We don't save every article in local database.
    //autoGenerate inclement id
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)