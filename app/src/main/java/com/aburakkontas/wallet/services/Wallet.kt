package com.aburakkontas.wallet.services

import com.aburakkontas.wallet.classes.BalanceDataResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class WalletService {
    private val walletApi = ServiceBuilder.buildWalletService()

    suspend fun checkBalance(token: String): BalanceDataResponse {
        val authHeader = "Bearer $token"

        return suspendCancellableCoroutine { continuation ->
            val request = walletApi.checkBalance(authHeader)

            request.enqueue(object : Callback<BalanceDataResponse> {
                override fun onFailure(call: Call<BalanceDataResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<BalanceDataResponse>, response: Response<BalanceDataResponse>) {
                    val result = response.body()
                    if (result != null) {
                        return continuation.resume(result)
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
}