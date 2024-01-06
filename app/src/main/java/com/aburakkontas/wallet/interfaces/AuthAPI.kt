package com.aburakkontas.wallet.interfaces

import com.aburakkontas.wallet.classes.LoginData
import com.aburakkontas.wallet.classes.LoginResponse
import com.aburakkontas.wallet.classes.RefreshTokenData
import com.aburakkontas.wallet.classes.RefreshTokenResponse
import com.aburakkontas.wallet.classes.RegisterData
import com.aburakkontas.wallet.classes.RegisterDataResponse
import com.aburakkontas.wallet.classes.ResetPasswordData
import com.aburakkontas.wallet.classes.ValidateTokenData

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthAPI {
    @Headers("Content-Type: application/json")
    @POST("/Auth/login")
    fun login(@Body loginData: LoginData): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("/Auth/refreshToken")
    fun refreshToken(@Body refreshToken: RefreshTokenData): Call<RefreshTokenResponse>

    @Headers("Content-Type: application/json")
    @GET("/Auth/register")
    fun register(@Body registerBody: RegisterData): Call<RegisterDataResponse>

    @Headers("Content-Type: application/json")
    @GET("/Auth/resetPassword")
    fun resetPassword(@Header("Authorization") bearerToken: String, @Body resetPasswordBody: ResetPasswordData): Call<Unit>

    @Headers("Content-Type: application/json")
    @GET("/Auth/validateToken")
    fun validateToken(@Body validateTokenBody: ValidateTokenData): Call<Boolean>

}