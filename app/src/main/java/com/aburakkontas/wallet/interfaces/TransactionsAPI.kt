package com.aburakkontas.wallet.interfaces

import com.aburakkontas.wallet.classes.DepositData
import com.aburakkontas.wallet.classes.LoginData
import com.aburakkontas.wallet.classes.LoginResponse
import com.aburakkontas.wallet.classes.RefreshTokenData
import com.aburakkontas.wallet.classes.RefreshTokenResponse
import com.aburakkontas.wallet.classes.RegisterData
import com.aburakkontas.wallet.classes.RegisterDataResponse
import com.aburakkontas.wallet.classes.SendMoneyData
import com.aburakkontas.wallet.classes.TransactionsData
import com.aburakkontas.wallet.classes.TransactionsDataResponse
import com.aburakkontas.wallet.classes.WithdrawData

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface TransactionsAPI {
    @Headers("Content-Type: application/json")
    @GET("/Transactions/sendMoney")
    fun sendMoney(@Header("Authorization") bearerToken: String, sendMoneyData: SendMoneyData): Call<Unit>

    @Headers("Content-Type: application/json")
    @GET("/Transactions/withdraw")
    fun withdraw(@Header("Authorization") bearerToken: String, withdrawMoneyData: WithdrawData): Call<Unit>

    @Headers("Content-Type: application/json")
    @GET("/Transactions/deposit")
    fun deposit(@Header("Authorization") bearerToken: String, depositMoneyData: DepositData): Call<Unit>

    @Headers("Content-Type: application/json")
    @GET("/Transactions/transactions")
    fun transactions(@Header("Authorization") bearerToken: String, transactions: TransactionsData): Call<TransactionsDataResponse>
}