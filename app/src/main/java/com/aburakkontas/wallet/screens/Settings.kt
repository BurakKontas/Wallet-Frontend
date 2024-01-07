package com.aburakkontas.wallet.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aburakkontas.wallet.LiveData
import com.aburakkontas.wallet.components.Logo
import com.aburakkontas.wallet.services.AuthService
import com.aburakkontas.wallet.services.LocalStorage
import com.aburakkontas.wallet.services.UsersService

@Composable
fun Settings(liveData: LiveData, navController: NavController) {
    val context = LocalContext.current
    val usersService = remember { UsersService() }

    LaunchedEffect(true) {
        usersService.getUserUsername(liveData.token.value!!, liveData.phone.value!!) {
            if (it != null) {
                liveData.username.value = it.username
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Language Selection
//        LanguageSelection()

        // Logo
        Logo()

        Spacer(modifier = Modifier.height(32.dp))

        // Username
        Text(text = "Username: ${liveData.username.value}")

        Spacer(modifier = Modifier.height(16.dp))

        // Phone
        Text(text = "Phone: ${liveData.phone.value}")

        Spacer(modifier = Modifier.height(16.dp))

        // Change Password
        ChangePasswordSection(liveData)

        Spacer(modifier = Modifier.height(16.dp))

        //Logout button
        LogoutButton(context, navController = navController)
    }
}

@Composable
fun ChangePasswordSection(liveData: LiveData) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val authService = remember { AuthService() }
    val context = LocalContext.current

    val customTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color(0x88333333),
        focusedLabelColor = Color(0xFF333333),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = currentPassword,
            onValueChange = { currentPassword = it },
            label = { Text("Current Password") },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            colors = customTextFieldColors,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.PlayArrow else Icons.Default.PlayArrow,
                        contentDescription = null
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            colors = customTextFieldColors,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.PlayArrow else Icons.Default.PlayArrow,
                        contentDescription = null
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authService.resetPassword(liveData.token.value!!, newPassword, currentPassword) {
                    if (it != null) {
                        Toast.makeText(context, "Password changed", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error changing password", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .border(1.5.dp, Color(0x88333333), shape = MaterialTheme.shapes.medium)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
            Text("Change Password", color = Color.Black, style = TextStyle(fontSize = 15.sp))
        }
    }
}

@Composable
fun LanguageSelection() {
    val selectedLanguage by remember { mutableStateOf("English") }
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
            Text("Language: $selectedLanguage", modifier = Modifier.clickable {
                // Handle language selection logic here
            })
        }
}

@Composable
fun LogoutButton(context: Context, navController: NavController) {
    val localStorage = LocalStorage.getInstance(context)

    Button(
        onClick = {
            localStorage.removeData("refreshToken")
            navController.navigate("login")
        },
        modifier = Modifier
            .border(1.5.dp, Color(0x88333333), shape = MaterialTheme.shapes.medium)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
        Text("Logout", color = Color.Black, style = TextStyle(fontSize = 15.sp))
    }
}