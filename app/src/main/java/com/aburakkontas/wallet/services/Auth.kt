package com.aburakkontas.wallet.services

import com.aburakkontas.wallet.classes.LoginData
import com.aburakkontas.wallet.classes.LoginResponse
import com.aburakkontas.wallet.interfaces.AuthAPI

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    fun login(username: String, password: String, onResult: (LoginResponse?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(AuthAPI::class.java)
        retrofit.login(LoginData(username, password)).enqueue(
            object: Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
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
}