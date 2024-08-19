package com.example.youtubeproject.data.remote.youtube

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor() : Interceptor {

    private val KEY="AIzaSyDpDV7GVlJ4fcEmDcM0uW2gmwkSC1QlByY"
    //AIzaSyC0HGcNrgdLphj9dX68pH8wF4K-bCRUPPM

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val newUrl = originRequest.url.newBuilder().addQueryParameter("key",KEY).build()
        val newRequest = originRequest.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}