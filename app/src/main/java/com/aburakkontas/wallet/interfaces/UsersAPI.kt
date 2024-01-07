package com.aburakkontas.wallet.interfaces

import com.aburakkontas.wallet.classes.BalanceDataResponse
import com.aburakkontas.wallet.classes.CheckContactsData
import com.aburakkontas.wallet.classes.CheckContactsDataResponse
import com.aburakkontas.wallet.classes.GetUsernameData
import com.aburakkontas.wallet.classes.GetUsernameDataResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface UsersAPI {
    @Headers("Content-Type: application/json")
    @POST("/User/checkcontacts")
    fun checkContacts(@Header("Authorization") bearerToken: String, @Body checkContactsData: CheckContactsData): Call<CheckContactsDataResponse>

    @Headers("Content-Type: application/json")
    @POST("/User/getusername")
    fun getUserUsername(@Header("Authorization") bearerToken: String, @Body getUsernameData: GetUsernameData): Call<GetUsernameDataResponse>
}