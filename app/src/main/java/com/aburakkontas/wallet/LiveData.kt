package com.aburakkontas.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aburakkontas.wallet.classes.Contact
import com.aburakkontas.wallet.classes.Language
import com.aburakkontas.wallet.services.AuthService

class LiveData : ViewModel() {
    val phone = MutableLiveData<String>("")
    val token = MutableLiveData<String>("")
    val refreshToken = MutableLiveData<String>("")
    val username = MutableLiveData<String>("")
    val balance = MutableLiveData<Double>(0.0)
    val contacts = MutableLiveData<List<Contact>>(listOf())
    val language = MutableLiveData<String>("en-US")
    val languageData = MutableLiveData<Language>()
}