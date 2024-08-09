package com.example.youtubeproject.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().addHeader(
            "Authorization",
            "Bearer %s".format("AIzaSyDpDV7GVlJ4fcEmDcM0uW2gmwkSC1QlByY")
        ).build()
        return chain.proceed(newRequest)
    }
}