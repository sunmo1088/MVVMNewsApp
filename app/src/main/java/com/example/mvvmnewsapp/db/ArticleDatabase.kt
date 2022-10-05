package com.example.mvvmnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mvvmnewsapp.models.Article

@Database(
    entities = [Article::class],
    version = 1
)
//Database fom room always need to be abstract class
//this database class will be used to access our article Dao which is
//used to access our actual database functions
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    //create companion object to be able to create our actual database
    companion object {
        //Volatile means that other thread can immediately see when
        //thread change this instance.
        @Volatile
        private var instance: ArticleDatabase? = null
        //We will use this to synchronize setting instance
        //so that we really make sure that there is only a single instance of our database at once
        private val LOCK = Any()

        //invoke function is called whenever we create an instance of our database like
        //ArticleDatabase()
        //if instance is null then start synchronized block with LOCK object that
        //means that everything that happens inside of this block of code here can't be accessed
        //by other threads at the same time so we really make sure that we don't set that there
        //is not another thread that sets this instance to something while we already set it
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            //check instance null check again
            //if null call createDatabase function and
            //set our instance to the result of our database function
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }
}