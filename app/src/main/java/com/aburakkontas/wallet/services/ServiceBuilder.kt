package com.aburakkontas.wallet.services

import com.aburakkontas.wallet.interfaces.AuthAPI
import com.aburakkontas.wallet.interfaces.TransactionsAPI
import com.aburakkontas.wallet.interfaces.WalletAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5256")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun buildAuthService(): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }

    fun buildWalletService(): WalletAPI {
        return retrofit.create(WalletAPI::class.java)
    }

    fun buildTransactionService(): TransactionsAPI {
        return retrofit.create(TransactionsAPI::class.java)
    }


}