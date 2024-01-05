package com.aburakkontas.wallet.interfaces

import com.aburakkontas.wallet.classes.LoginData
import com.aburakkontas.wallet.classes.LoginResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthAPI {
    @Headers("Content-Type: application/json")
    @POST("users")
    fun login(@Body loginData: LoginData): Call<LoginResponse>
}