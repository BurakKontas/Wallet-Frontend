package com.aburakkontas.wallet

import AppNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val liveData = LiveData()

        setContent {
            AppNavigation(liveData)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val liveData = LiveData()

    AppNavigation(liveData)
}