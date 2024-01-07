package com.aburakkontas.wallet.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aburakkontas.wallet.LiveData
import com.aburakkontas.wallet.services.TransactionsService

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Transactions(liveData: LiveData, navController: NavController) {
    val transactionsService = remember { TransactionsService() }
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Transactions",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium
        )
        TransactionsList(transactionsService = transactionsService, token = liveData.token.value!!, navController = navController, context, limit = 100)
    }
}