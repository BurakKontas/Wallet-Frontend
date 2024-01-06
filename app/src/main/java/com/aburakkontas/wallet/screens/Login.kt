package com.aburakkontas.wallet.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aburakkontas.wallet.LiveData
import com.aburakkontas.wallet.components.Logo
import com.aburakkontas.wallet.services.AuthService
import com.aburakkontas.wallet.services.LocalStorage

@Composable
fun Login(navController: NavController, liveData: LiveData) {
    val phoneNumber = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    val authService = remember { AuthService() }
    val context = LocalContext.current

    LaunchedEffect(true) {
        val localStorage = LocalStorage.getInstance(context)
        val refreshToken = localStorage.getData("refreshToken", "")
        print("refresh token: $refreshToken")
        if (refreshToken != "") {
            authService.refreshToken(refreshToken) {
                if (it != null) {
                    liveData.token.value = it.token
                    liveData.refreshToken.value = refreshToken
                    liveData.username.value = it.username
                    liveData.phone.value = it.phone

                    navController.navigate("home")
                } else {
                    Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Phone Number") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                )
                Text("Remember Me")
            }
        }

        Button(
            onClick = {
                authService.login(phoneNumber.value, password.value) {
                    if (it != null) {
                        liveData.token.value = it.token
                        liveData.phone.value = it.phone
                        liveData.refreshToken.value = it.refreshToken
                        val localStorage = LocalStorage.getInstance(context)
                        if (rememberMe) {
                            localStorage.saveData("refreshToken", it.refreshToken)
                        } else {
                            localStorage.removeData("refreshToken")
                        }
                        navController.navigate("home")
                    } else {
                        Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}
