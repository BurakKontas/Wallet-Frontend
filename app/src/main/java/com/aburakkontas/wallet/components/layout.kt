package com.aburakkontas.wallet.components

import android.widget.Space
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aburakkontas.wallet.LiveData

@Composable
fun Layout(navController: NavController, liveData: LiveData, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        content()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BottomNavigationButton(label = "HomePage", icon= Icons.Default.Home) {
                navController.navigate("home")
            }
            BottomNavigationButton(label = "SendMoney", icon= Icons.Default.Send) {
                navController.navigate("send")
            }
            BottomNavigationButton(label = "Transactions", icon= Icons.Default.Info) {
                navController.navigate("transactions")
            }
            BottomNavigationButton(label = "Settings", icon= Icons.Default.Settings) {
                navController.navigate("settings")
            }
        }
    }
}

@Composable
fun BottomNavigationButton(
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick : () -> Unit,
) {
    Surface(
        modifier = modifier
            .padding(top = 4.dp)
            .clickable() { onClick.invoke() },
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(3.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = Color(0xBA000011)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = label,
                modifier = Modifier.padding(horizontal = 10.dp),
                color = Color(0xBA000011)
            )
        }
    }
}