package com.example.mvvmnewsapp.util


//it is recommended by Google
//it is used to wrap around our network responses and useful to differentiate between
//successful and error response and also helps to handle the loading state so when we make
//a response that we can show on a progress bar while that response is processing and we get
//the answer then we can use that resource class to tell us whether that answer was successful
//or an error and depending on that we can handle that error or show that successful response

//sealed class is a kind of abstract class but we can define which classes are allowed to inherit
//from that resource class.
//here we will define 3 different classes and only those are allowed to inherit from resource
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}