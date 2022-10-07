package com.example.mvvmnewsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmnewsapp.R
import com.example.mvvmnewsapp.models.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    //it is used pass list of item(list of article) like this
    //class NewsAdapter(list: List<Article> ...
    //and used to notify data set change
    //but this is inefficient because the recycler view adapter will always update
    //its whole item even the items that didn't changed.
    //to solve problem we can use diffutils that calculate the difference between two lists
    //and only update items that were different
    //and other benefit is it runs on background so it don't block the main thread.
    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)


    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            //here we usually use id but item from api does not have id sometime in this case
            //so we use url here that also unique
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    //take two list and compare them and calculates the differences
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            tvSource.text = article.source.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt
            //'setOnClickListener' refers to our item view whenever we click on that item
            //we want to check if our own item click listener is not equal to null so we go into
            //let block and call that function(onItemClickListener)
            setOnClickListener {
                //'it' refer to our own click listener lambda function(onItemClickListener)
                // which take article as parameter
                //
                onItemClickListener?.let { it(article) }
            }
        }
    }

    //pass current article when we click on an item to that lambda function so that
    //it will be able to open the correct web view page
    private var onItemClickListener: ((Article) -> Unit)? = null

    //this take listener that also takes an article as a parameter returns unit
    //
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        //just want to set our own item click listener to our passed listener
        //so that is just good coding style that we manage the item clicks outside
        //our news adapter
        onItemClickListener = listener
    }
}