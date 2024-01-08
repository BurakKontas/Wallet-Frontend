package com.aburakkontas.wallet.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aburakkontas.wallet.LiveData
import com.aburakkontas.wallet.components.Logo

@Composable
fun Login(liveData: LiveData) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    val customTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color(0x880069a5),
        focusedLabelColor = Color(0xFF0069a5),
    )

    LaunchedEffect(true) {
        liveData.refreshToken()
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
            onClick = { liveData.login(username.value, password.value) },
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
                liveData.navigate("register")
            },
        ) {
            Text("Register", color = Color.Black, style = TextStyle(fontSize = 15.sp))
        }
    }
}
