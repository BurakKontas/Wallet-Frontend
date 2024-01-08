package com.aburakkontas.wallet.services

import com.aburakkontas.wallet.classes.DepositData
import com.aburakkontas.wallet.classes.SendMoneyData
import com.aburakkontas.wallet.classes.TransactionsData
import com.aburakkontas.wallet.classes.TransactionsDataResponse
import com.aburakkontas.wallet.classes.WithdrawData
import com.aburakkontas.wallet.enums.TransactionMode
import com.aburakkontas.wallet.interfaces.TransactionsAPI
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class TransactionsService {
    private val transactionsApi: TransactionsAPI = ServiceBuilder.buildTransactionService()

    suspend fun sendMoney(bearerString: String, recipientPhone: String, amount: Double): Boolean {
        val authHeader = "Bearer $bearerString"

        return suspendCancellableCoroutine { continuation ->
            val request = transactionsApi.sendMoney(authHeader, SendMoneyData(recipientPhone, amount))

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

    suspend fun withdraw(bearerString: String, amount: Double): Boolean {
        val authHeader = "Bearer $bearerString"

        return suspendCancellableCoroutine { continuation ->
            val request = transactionsApi.withdraw(authHeader, WithdrawData(amount))

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

    suspend fun deposit(bearerString: String, amount: Double): Boolean {
        val authHeader = "Bearer $bearerString"
        return suspendCancellableCoroutine { continuation ->
            val request = transactionsApi.deposit(authHeader, DepositData(amount))

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

    suspend fun getTransactions(bearerString: String, limit: Int = 10, mode: TransactionMode): TransactionsDataResponse? {
        val authHeader = "Bearer $bearerString"

        return suspendCancellableCoroutine { continuation ->
            val request = transactionsApi.getTransactions(authHeader, TransactionsData(limit, mode.ordinal))

            request.enqueue(object : Callback<TransactionsDataResponse> {
                override fun onFailure(call: Call<TransactionsDataResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<TransactionsDataResponse>, response: Response<TransactionsDataResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        continuation.resume(result)
                    } else {
                        continuation.resumeWithException(Exception("Request failed with code ${response.code()}"))
                    }
                }
            })
            continuation.invokeOnCancellation {
                request.cancel()
            }
        }
    }
}