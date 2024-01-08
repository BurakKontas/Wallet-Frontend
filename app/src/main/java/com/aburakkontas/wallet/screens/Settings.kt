package com.aburakkontas.wallet.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aburakkontas.wallet.LiveData
import com.aburakkontas.wallet.components.Logo

@Composable
fun Settings(liveData: LiveData) {
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
        LogoutButton(liveData)
    }
}

@Composable
fun ChangePasswordSection(liveData: LiveData) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val customTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color(0x880069a5),
        focusedLabelColor = Color(0xFF0069a5),
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
            onClick = { liveData.resetPassword(currentPassword, newPassword) },
            modifier = Modifier
                .border(1.5.dp, Color(0x880069a5), shape = MaterialTheme.shapes.medium)
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
fun LogoutButton(liveData: LiveData) {

    Button(
        onClick = {
            liveData.logout()
        },
        modifier = Modifier
            .border(1.5.dp, Color(0x880069a5), shape = MaterialTheme.shapes.medium)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
        Text("Logout", color = Color.Black, style = TextStyle(fontSize = 15.sp))
    }
}