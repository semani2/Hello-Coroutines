package com.sai.news_app

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine {continuation ->
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    if (response.errorBody() != null) {
                        continuation.resumeWithException(Exception(response.message()))
                    } else {
                        continuation.resume(response.body()!!)
                    }
                } else {
                    continuation.resumeWithException(Exception(response.message()))
                }
            }
        })
    }
}
