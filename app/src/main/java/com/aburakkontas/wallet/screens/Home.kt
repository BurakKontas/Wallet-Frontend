package com.aburakkontas.wallet.screens

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.ScrollView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aburakkontas.wallet.LiveData
import com.aburakkontas.wallet.R
import com.aburakkontas.wallet.classes.Transaction
import com.aburakkontas.wallet.classes.TransactionsDataResponse
import com.aburakkontas.wallet.enums.TransactionMode
import com.aburakkontas.wallet.services.TransactionsService
import com.aburakkontas.wallet.services.UsersService
import com.aburakkontas.wallet.services.WalletService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(liveData: LiveData, navController: NavController) {
    val walletService = remember { WalletService() }
    val transactionsService = remember { TransactionsService() }
    val context = LocalContext.current

    LaunchedEffect(true) {
        walletService.checkBalance(liveData.token.value!!) {
            if (it != null) {
                liveData.balance.value = it.balance
            } else {
                Log.d("Home", "Balance check failed")
            }
        }
    }
    Column {
        HomeHeader(navController = navController)
        BalanceCard(liveData.balance.value!!, navController = navController, liveData = liveData)
        TransactionsButton(liveData, navController)
        TransactionsList(transactionsService = transactionsService, token = liveData.token.value!!, navController = navController, context, limit = 10, liveData = liveData)
    }
}

@Composable
fun TransactionsButton(liveData:LiveData, navController: NavController) {
    Text(
        text = "Transactions ->",
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 8.dp)
            .clickable {
                navController.navigate("transactions")
            },
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
    )

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun HomeHeader(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Wallet",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .clickable { navController.navigate("settings") }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.photo),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Composable
fun BalanceCard(balance: Double, navController: NavController, liveData: LiveData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.navigate("withdraw") },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Withdraw",
                    tint = Color.Red
                )
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = "Balance",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$balance TL",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            IconButton(
                onClick = { navController.navigate("deposit") },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Deposit",
                    tint = Color.Green
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionsList(transactionsService: TransactionsService, token: String, navController: NavController, context: Context, limit: Int, liveData: LiveData) {
    val transactions = remember { mutableStateOf(listOf<Transaction>()) }

    LaunchedEffect(true) {
        transactionsService.getTransactions(bearerString = token, limit = limit, mode = TransactionMode.All) {
            if (it != null) {
                transactions.value = it.transactions
            } else {
                Log.d("Home", "Transactions check failed")
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        )
        {
            items(transactions.value.size) { index ->
                TransactionCard(transaction = transactions.value[index], liveData = liveData)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionCard(transaction: Transaction, liveData: LiveData) {
    val usersService = remember { UsersService() }
    val username = remember { mutableStateOf("") }
    val user = if (transaction.mode == TransactionMode.Send.ordinal) transaction.receiverPhone else transaction.senderPhone

    val alphaValue = 0.2f
    val redColor = Color(1.0f, 0.0f, 0.0f, alphaValue)
    val greenColor = Color(0.0f, 1.0f, 0.0f, alphaValue)

    val rawDate = transaction.date
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

    val transactionText = TransactionMode.values().firstOrNull() { it.ordinal == transaction.mode }?.text ?: ""

    val formattedDate = LocalDateTime.parse(rawDate.substring(0, 23), formatter)
        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))

    val color = if (transaction.mode == TransactionMode.Send.ordinal || transaction.mode == TransactionMode.Withdraw.ordinal) {
        redColor
    } else {
        greenColor
    }

    LaunchedEffect(true) {
        usersService.getUserUsername(token = liveData.token.value!!, userPhone = user) {
            if (it != null) {
                username.value = it.username
            } else {
                username.value = "Unknown"
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = color, shape = MaterialTheme.shapes.medium),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp)
                        .background(Color.LightGray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.photo),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = transactionText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = "${username.value} (${user})",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Text(
                text = (if (transaction.mode == TransactionMode.Send.ordinal) "-" else "") + transaction.amount.toString() + " TL",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}