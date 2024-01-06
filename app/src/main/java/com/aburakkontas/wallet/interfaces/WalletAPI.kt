package com.aburakkontas.wallet.interfaces

import com.aburakkontas.wallet.classes.BalanceDataResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface WalletAPI {
    @Headers("Content-Type: application/json")
    @GET("/Wallet/balance")
    fun checkBalance(@Header("Authorization") bearerToken: String): Call<BalanceDataResponse>
}