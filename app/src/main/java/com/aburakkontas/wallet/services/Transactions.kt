package com.aburakkontas.wallet.services

import com.aburakkontas.wallet.classes.DepositData
import com.aburakkontas.wallet.classes.RegisterDataResponse
import com.aburakkontas.wallet.classes.SendMoneyData
import com.aburakkontas.wallet.classes.TransactionsData
import com.aburakkontas.wallet.classes.TransactionsDataResponse
import com.aburakkontas.wallet.classes.WithdrawData
import com.aburakkontas.wallet.enums.TransactionMode
import com.aburakkontas.wallet.interfaces.TransactionsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionsService {
    private val transactionsApi: TransactionsAPI = ServiceBuilder.buildTransactionService()

    fun sendMoney(bearerString: String, recipientPhone: String, amount: Int, onResult: (Unit?) -> Unit) {
        val authHeader = "Bearer $bearerString"

        val request = transactionsApi.sendMoney(authHeader, SendMoneyData(recipientPhone, amount))

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

    fun withdraw(bearerString: String, amount: Int, onResult: (Unit?) -> Unit) {
        val authHeader = "Bearer $bearerString"

        val request = transactionsApi.withdraw(authHeader, WithdrawData(amount))

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

    fun deposit(bearerString: String, amount: Int, onResult: (Unit?) -> Unit) {
        val authHeader = "Bearer $bearerString"

        val request = transactionsApi.deposit(authHeader, DepositData(amount))

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

    fun getTransactions(bearerString: String, limit: Int = 10, mode: TransactionMode, onResult: (TransactionsDataResponse?) -> Unit) {
        val authHeader = "Bearer $bearerString"

        val request = transactionsApi.getTransactions(authHeader, TransactionsData(limit, mode.ordinal))

        request.enqueue(
            object: Callback<TransactionsDataResponse> {
                override fun onFailure(call: Call<TransactionsDataResponse>, t: Throwable) {
                    t.printStackTrace()
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<TransactionsDataResponse>,
                    response: Response<TransactionsDataResponse>
                ) {
                    val result = response.body();
                    onResult(result);
                }
            }
        )
    }
}