package com.aburakkontas.wallet.services

import com.aburakkontas.wallet.classes.CheckContactsData
import com.aburakkontas.wallet.classes.CheckContactsDataResponse
import com.aburakkontas.wallet.classes.GetUsernameData
import com.aburakkontas.wallet.classes.GetUsernameDataResponse
import com.aburakkontas.wallet.interfaces.UsersAPI
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UsersService {
    private val usersApi: UsersAPI = ServiceBuilder.buildUsersService()

    suspend fun checkContacts(token: String, contacts: List<String>): CheckContactsDataResponse {
        val authHeader = "Bearer $token"

        return suspendCancellableCoroutine { continuation ->
            val request = usersApi.checkContacts(authHeader, CheckContactsData(contacts))

            request.enqueue(object : Callback<CheckContactsDataResponse> {
                override fun onFailure(call: Call<CheckContactsDataResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<CheckContactsDataResponse>, response: Response<CheckContactsDataResponse>) {
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

    suspend fun getUserUsername(token: String, userPhone:String): GetUsernameDataResponse {
        val authHeader = "Bearer $token"

        return suspendCancellableCoroutine { continuation ->
            val request = usersApi.getUserUsername(authHeader, GetUsernameData(userPhone))

            request.enqueue(object : Callback<GetUsernameDataResponse> {
                override fun onFailure(call: Call<GetUsernameDataResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<GetUsernameDataResponse>, response: Response<GetUsernameDataResponse>) {
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