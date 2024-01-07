package com.aburakkontas.wallet.screens

import android.widget.Toast
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aburakkontas.wallet.LiveData
import com.aburakkontas.wallet.components.Logo
import com.aburakkontas.wallet.services.AuthService
import com.aburakkontas.wallet.services.LocalStorage

@Composable
fun Login(navController: NavController, liveData: LiveData) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    val authService = remember { AuthService() }
    val context = LocalContext.current

    val customTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color(0x880069a5),
        focusedLabelColor = Color(0xFF333333),
    )

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
        Text("Login", style = TextStyle(fontSize = 15.sp))
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            singleLine = true,
            colors = customTextFieldColors,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            singleLine = true,
            colors = customTextFieldColors,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = Color.White,
                        checkedColor = Color(0x880069a5),
                        uncheckedColor = Color(0x880069a5),
                    )
                )
                Text("Remember Me")
            }
        }

        Button(
            modifier = Modifier
                .border(1.5.dp, Color(0x880069a5), shape = MaterialTheme.shapes.medium)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            onClick = {
                authService.login(username.value, password.value) {
                    if (it != null) {
                        liveData.token.value = it.token
                        liveData.phone.value = it.phone
                        liveData.refreshToken.value = it.refreshToken
                        liveData.username.value = it.username

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
        ) {
            Text("Login", color = Color.Black, style = TextStyle(fontSize = 15.sp))
        }
        Button(
            modifier = Modifier
                .padding(top = 10.dp)
                .border(1.5.dp, Color(0x880069a5), shape = MaterialTheme.shapes.medium)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            onClick = {
                navController.navigate("register")
            },
        ) {
            Text("Register", color = Color.Black, style = TextStyle(fontSize = 15.sp))
        }
    }
}
