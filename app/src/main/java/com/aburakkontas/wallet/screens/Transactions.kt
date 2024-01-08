package com.aburakkontas.wallet.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aburakkontas.wallet.LiveData

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Transactions(liveData: LiveData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TransactionsList(limit = 100, liveData = liveData, dateText = true)
    }
}