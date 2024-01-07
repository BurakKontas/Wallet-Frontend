package com.aburakkontas.wallet.services

import com.aburakkontas.wallet.classes.BalanceDataResponse
import com.aburakkontas.wallet.classes.CheckContactsData
import com.aburakkontas.wallet.classes.CheckContactsDataResponse
import com.aburakkontas.wallet.classes.GetUsernameData
import com.aburakkontas.wallet.classes.GetUsernameDataResponse
import com.aburakkontas.wallet.interfaces.UsersAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersService {
    private val usersApi: UsersAPI = ServiceBuilder.buildUsersService()

    fun checkContacts(token: String, contacts: List<String>,  onResult: (CheckContactsDataResponse?) -> Unit) {
        val authHeader = "Bearer $token"

        val request = usersApi.checkContacts(authHeader, CheckContactsData(contacts))

        request.enqueue(
            object: Callback<CheckContactsDataResponse> {
                override fun onFailure(call: Call<CheckContactsDataResponse>, t: Throwable) {
                    t.printStackTrace()
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<CheckContactsDataResponse>,
                    response: Response<CheckContactsDataResponse>
                ) {
                    val result = response.body();
                    onResult(result);
                }
            }
        )
    }

    fun getUserUsername(token: String, userPhone:String, onResult: (GetUsernameDataResponse?) -> Unit) {
        val authHeader = "Bearer $token"

        val request = usersApi.getUserUsername(authHeader, GetUsernameData(userPhone))

        request.enqueue(
            object: Callback<GetUsernameDataResponse> {
                override fun onFailure(call: Call<GetUsernameDataResponse>, t: Throwable) {
                    t.printStackTrace()
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<GetUsernameDataResponse>,
                    response: Response<GetUsernameDataResponse>
                ) {
                    val result = response.body();
                    onResult(result);
                }
            }
        )
    }
}