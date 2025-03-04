package com.example.watchme.core.network

import com.example.watchme.core.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZGQ5Y2YzMmI0MzliZGM4NjUxNjYxYWNiNjYzYjRhOCIsIm5iZiI6MTczODM0ODM4NC4xODcsInN1YiI6IjY3OWQxNzYwMDRjYjZmNDFiOWNiMjZlOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.QP-sqr1P-xsSkAydu6xw66288g8tDHkmT55PcY6AAZk")
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .build()

        return chain.proceed(request)
    }
}