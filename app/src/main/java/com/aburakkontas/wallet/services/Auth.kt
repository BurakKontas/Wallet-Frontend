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
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class AuthService {
    private val loginApi: AuthAPI = ServiceBuilder.buildAuthService()

    suspend fun login(username: String, password: String): LoginResponse {
        return suspendCancellableCoroutine { continuation ->
            val request = loginApi.login(LoginData(username, password))


            request.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result != null) {
                            return continuation.resume(result)
                        } else {
                            continuation.resumeWithException(Exception("Invalid credentials"))
                        }
                    } else {
                        continuation.resumeWithException(Exception("Invalid credentials"))
                    }
                }
            })
            continuation.invokeOnCancellation {
                request.cancel()
            }
        }
    }
    suspend fun refreshToken(refreshToken: String): RefreshTokenResponse {

        return suspendCancellableCoroutine { continuation ->
            val request = loginApi.refreshToken(RefreshTokenData(refreshToken))

            request.enqueue(object : Callback<RefreshTokenResponse> {
                override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<RefreshTokenResponse>, response: Response<RefreshTokenResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result != null) {
                            return continuation.resume(result)
                        } else {
                            continuation.resumeWithException(Exception("Invalid credentials"))
                        }
                    } else {
                        continuation.resumeWithException(Exception("Invalid credentials"))
                    }
                }
            })
            continuation.invokeOnCancellation {
                request.cancel()
            }
        }
    }

    suspend fun register(phone: String, password: String, username: String): RegisterDataResponse {

        return suspendCancellableCoroutine { continuation ->
            val request = loginApi.register(RegisterData(phone, password, username))

            request.enqueue(object : Callback<RegisterDataResponse> {
                override fun onFailure(call: Call<RegisterDataResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<RegisterDataResponse>, response: Response<RegisterDataResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result != null) {
                            return continuation.resume(result)
                        } else {
                            continuation.resumeWithException(Exception("Invalid credentials"))
                        }
                    } else {
                        continuation.resumeWithException(Exception("Invalid credentials"))
                    }
                }
            })
            continuation.invokeOnCancellation {
                request.cancel()
            }
        }
    }

    suspend fun resetPassword(bearerToken: String, password: String, currentPassword: String): Boolean {
        val authHeader = "Bearer $bearerToken"

        return suspendCancellableCoroutine { continuation ->
            val request = loginApi.resetPassword(authHeader, ResetPasswordData(password, currentPassword))

            request.enqueue(object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    return continuation.resume(response.isSuccessful)
                }
            })
            continuation.invokeOnCancellation {
                request.cancel()
            }
        }
    }

    suspend fun validateToken(token: String): Boolean {

        return suspendCancellableCoroutine { continuation ->
            val request = loginApi.validateToken(ValidateTokenData(token))

            request.enqueue(object : Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    return if(response.isSuccessful) continuation.resume(response.body()!!)
                    else continuation.resume(false)
                }
            })
            continuation.invokeOnCancellation {
                request.cancel()
            }
        }
    }
}