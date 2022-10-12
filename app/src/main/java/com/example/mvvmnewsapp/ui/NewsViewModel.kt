package com.example.mvvmnewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmnewsapp.models.NewsResponse
import com.example.mvvmnewsapp.repository.NewsRepository
import com.example.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    //we cannot use constructor parameters by default for our own viewmodels if
    //we want to do that and we really need that here because we need our news repository
    //in our viewmodel then we need to create what is called a viewmodel provider factory
    //to define how our own viewmodel should be created.

    //we have a instance of newsRepository and will call the function from newsRepository
    //we will handle the responses of our requests and then we will have livedata object that will
    //notify all of our fragments about changes regarding these requests
    val newsRepository: NewsRepository
) : ViewModel() {

    //livedata object here is that used for fragments so that can subscribe to that livedata as observer
    //it is very useful when we rotate device then we immediately get the current up-to-date
    //data from our viewModel
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    init {
        getBreakingNews("us")
    }
    //we don't want to make this function as suspend function because it will just
    //propagate that function to our fragment and then we would need to start the coroutine
    //in the fragment that we don't want that, So what we need to do is that we need to start a
    //coroutine in this function.
    //To start coroutine in viewModel, start with viewModelScope
    //viewModelScope will make sure that this coroutine stays only as long as our viewModel
    //is alive.
    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        //emit loading state first.
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))

    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            //we need to check body of our response in not equal to null
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}