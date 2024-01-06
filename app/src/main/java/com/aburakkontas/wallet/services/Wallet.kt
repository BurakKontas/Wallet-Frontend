package com.aburakkontas.wallet.services

import com.aburakkontas.wallet.classes.BalanceDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WalletService {
    private val walletApi = ServiceBuilder.buildWalletService()

    fun checkBalance(token: String, onResult: (BalanceDataResponse?) -> Unit) {
        val authHeader = "Bearer $token"

        val request = walletApi.checkBalance(authHeader)

        request.enqueue(
            object: Callback<BalanceDataResponse> {
                override fun onFailure(call: Call<BalanceDataResponse>, t: Throwable) {
                    t.printStackTrace()
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<BalanceDataResponse>,
                    response: Response<BalanceDataResponse>
                ) {
                    val result = response.body();
                    onResult(result);
                }
            }
        )
    }
}