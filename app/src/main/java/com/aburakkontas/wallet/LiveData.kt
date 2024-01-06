package com.aburakkontas.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aburakkontas.wallet.services.AuthService

class LiveData : ViewModel() {
    val phone = MutableLiveData<String>("")
    val token = MutableLiveData<String>("")
    val refreshToken = MutableLiveData<String>("")
    val username = MutableLiveData<String>("")
}