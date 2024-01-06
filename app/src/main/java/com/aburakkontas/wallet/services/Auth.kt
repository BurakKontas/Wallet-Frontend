package com.aburakkontas.wallet.services

import com.aburakkontas.wallet.classes.LoginData
import com.aburakkontas.wallet.classes.LoginResponse
import com.aburakkontas.wallet.classes.RefreshTokenData
import com.aburakkontas.wallet.classes.RefreshTokenResponse
import com.aburakkontas.wallet.classes.RegisterData
import com.aburakkontas.wallet.classes.RegisterDataResponse
import com.aburakkontas.wallet.classes.ResetPasswordData
import com.aburakkontas.wallet.classes.ValidateTokenData
import com.aburakkontas.wallet.interfaces.AuthAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class AuthService {
    private val loginApi: AuthAPI = ServiceBuilder.buildAuthService()

    fun login(username: String, password: String, onResult: (LoginResponse?) -> Unit) {
        val request = loginApi.login(LoginData(username, password))

        request.enqueue(
            object: Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    t.printStackTrace()
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val result = response.body();
                    onResult(result);
                }
            }
        )
    }
    fun refreshToken(refreshToken: String, onResult: (RefreshTokenResponse?) -> Unit) {
        val request = loginApi.refreshToken(RefreshTokenData(refreshToken))

        request.enqueue(
            object: Callback<RefreshTokenResponse> {
                override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {
                    t.printStackTrace()
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<RefreshTokenResponse>,
                    response: Response<RefreshTokenResponse>
                ) {
                    val result = response.body();
                    onResult(result);
                }
            }
        )
    }

    fun register(phone: String, password: String, username: String, onResult: (RegisterDataResponse?) -> Unit) {
        val request = loginApi.register(RegisterData(phone, password, username))

        request.enqueue(
            object: Callback<RegisterDataResponse> {
                override fun onFailure(call: Call<RegisterDataResponse>, t: Throwable) {
                    t.printStackTrace()
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<RegisterDataResponse>,
                    response: Response<RegisterDataResponse>
                ) {
                    val result = response.body();
                    onResult(result);
                }
            }
        )
    }

    fun resetPassword(bearerToken: String, password: String, onResult: (Unit?) -> Unit) {
        val authHeader = "Bearer $bearerToken"

        val request = loginApi.resetPassword(authHeader, ResetPasswordData(password))

        request.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                t.printStackTrace()
                onResult(null)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                val result = response.body()
                onResult(result)
            }
        })
    }

    fun validateToken(token: String, onResult: (Boolean?) -> Unit) {
        val request = loginApi.validateToken(ValidateTokenData(token))

        request.enqueue(
            object: Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    t.printStackTrace()
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    val result = response.body();
                    onResult(result);
                }
            }
        )
    }
}