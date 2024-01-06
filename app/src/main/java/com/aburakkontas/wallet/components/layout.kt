package com.aburakkontas.wallet.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.aburakkontas.wallet.LiveData

@Composable
fun Layout(navController: NavController, liveData: LiveData, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        content()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavigationButton(label = "home") {
                navController.navigate("home")
            }
            BottomNavigationButton(label = "send") {
                navController.navigate("send")
            }
            BottomNavigationButton(label = "history") {
                navController.navigate("history")
            }
            BottomNavigationButton(label = "profile") {
                navController.navigate("profile")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick : () -> Unit,
) {
    Surface(
        modifier = modifier,
        onClick = {onClick.invoke()}
    ) {
        Text(text = label)
    }
}