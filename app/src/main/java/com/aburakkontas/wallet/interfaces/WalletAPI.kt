package com.aburakkontas.wallet.interfaces

import com.aburakkontas.wallet.classes.BalanceDataResponse
import com.aburakkontas.wallet.classes.LoginData
import com.aburakkontas.wallet.classes.LoginResponse
import com.aburakkontas.wallet.classes.RefreshTokenData
import com.aburakkontas.wallet.classes.RefreshTokenResponse
import com.aburakkontas.wallet.classes.RegisterData
import com.aburakkontas.wallet.classes.RegisterDataResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface WalletAPI {
    @Headers("Content-Type: application/json")
    @GET("/Wallet/balance")
    fun checkBalance(@Header("Authorization") bearerToken: String): Call<BalanceDataResponse>
}