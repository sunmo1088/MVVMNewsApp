package com.example.mvvmnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmnewsapp.models.Article

//DAO - Data Access Object
//It is like NewsAPI interface. We define the function how we can access local database

@Dao
interface ArticleDao {

    //onConflict strategy determine if the article we want to save is already exist.
    //In that case, we simply want to replace
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //it return Long that is the ID that was inserted
    suspend fun upsert(article: Article): Long


    @Query("SELECT * FROM articles")
    //here use normal function not suspend function because
    //this function will return a livedata object that does not work with suspend function.
    //LiveData<List<Article>> means
    //whenever an article inside of that list change then this livedata will notify all of
    //its observers that subscribed to changes of changes of that livedata.
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}