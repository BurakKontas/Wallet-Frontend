package com.aburakkontas.wallet.screens

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.aburakkontas.wallet.LiveData

@Composable
fun Home(liveData: LiveData) {
    LaunchedEffect(true) { // Her sayfa değişiminde değişiyor burada balanceyi çekebiliriz
        Log.d("token", "token: ${liveData.token.value}")
        Log.d("refresh token", "refresh token: ${liveData.refreshToken.value}")
        Log.d("username", "username: ${liveData.username.value}")
        Log.d("phone", "phone: ${liveData.phone.value}")
    }
    Text(text = "Home")
}