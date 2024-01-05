package com.aburakkontas.wallet.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = try {
        Retrofit.Builder()
            .baseUrl("http://127.0.0.1")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    } catch (e: Exception) {
        print(e)
        throw e
    }

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}