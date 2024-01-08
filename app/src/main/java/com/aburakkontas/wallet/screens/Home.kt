package com.aburakkontas.wallet.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aburakkontas.wallet.LiveData
import com.aburakkontas.wallet.R
import com.aburakkontas.wallet.classes.Transaction
import com.aburakkontas.wallet.components.Logo
import com.aburakkontas.wallet.enums.TransactionMode
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(liveData: LiveData) {
    LaunchedEffect(true) {
        liveData.updateBalance()
    }
    Column {
        HomeHeader(liveData = liveData)
        BalanceCard(liveData = liveData)
        TransactionsButton(liveData)
        TransactionsList(limit = 5, liveData = liveData)
    }
}

@Composable
fun TransactionsButton(liveData:LiveData) {
    Row(
        modifier = Modifier
            .padding(start = 15.dp)
            .clickable {
                liveData.navigate("transactions")
            },
    ) {
        Text(
            text = "TRANSACTIONS",
            modifier = Modifier
                .padding(top = 3.7.dp, start = 0.dp, bottom = 0.dp, end = 5.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Box(modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth(0.1f)
        )
        {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Arrow", modifier = Modifier.size(20.dp))
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun HomeHeader(liveData: LiveData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(27.dp))
            Logo(modifier = Modifier.size(70.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.clickable { liveData.navigate("settings") }

            ) {
                Column(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.photo),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Profile", modifier = Modifier.alpha(0.7f))
            }
        }
    }
}

@Composable
fun BalanceCard(liveData: LiveData) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.clickable(
                    onClick = { liveData.navigate("withdraw") },
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Withdraw",
                    tint = Color.Red,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Withdraw", modifier = Modifier.alpha(0.7f))
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Balance", modifier = Modifier.alpha(0.7f))
                Text(
                    text = "₺${liveData.balance.value!!}",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight(700), fontSize = 27.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Column(
                modifier = Modifier.clickable(
                    onClick = { liveData.navigate("deposit") },
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Deposit",
                    tint = Color.Green,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Deposit", modifier = Modifier.alpha(0.7f))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionsList(limit: Int, liveData: LiveData, dateText: Boolean = false) {
    val transactionsMap = mutableMapOf<LocalDate, MutableList<Transaction>>()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    for(transaction in liveData.transactions.value!!) {
        val dateStr = transaction.date.split("T")[0]
        val date = LocalDate.parse(dateStr, formatter)

        if(transactionsMap.containsKey(date)) {
            transactionsMap[date]?.add(transaction)
        } else {
            transactionsMap[date] = mutableListOf(transaction)
        }
    }

    LaunchedEffect(true) {
        liveData.getTransactions(limit)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
    )
    {
        itemsIndexed(transactionsMap.keys.toList()) { _, date ->
            if(dateText) TransactionDate(date = date)
            transactionsMap[date]?.forEach { transaction ->
                TransactionCard(transaction = transaction, liveData = liveData)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun monthDateConverter(date: LocalDate): String {
    val monthName = when(date.format(DateTimeFormatter.ofPattern("MM")).toInt()) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> "Unknown"
    }
    return monthName
}

@SuppressLint("WeekBasedYear")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionDate(date: LocalDate) {
    var dateString = date.toString()
    if(dateString == LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"))) {
        dateString = "Today"
    } else {
        dateString = date.format(DateTimeFormatter.ofPattern("dd MM yyyy"))
        val monthName = monthDateConverter(date)
        dateString = dateString.replaceRange(3..4, monthName)
    }
    Text(
        text = dateString,
        modifier = Modifier
            .padding(start = 20.dp, top = 10.dp, bottom = 5.dp)
            .alpha(0.7f),
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionCard(transaction: Transaction, liveData: LiveData) {
    val username = remember { mutableStateOf("") }
    val user = if (transaction.mode == TransactionMode.Send.ordinal) transaction.receiverPhone else transaction.senderPhone

    val alphaValue = 0.6f
    val redColor = Color(1.0f, 0.0f, 0.0f, alphaValue)
    val greenColor = Color(0.0f, 1.0f, 0.0f, alphaValue)

    val rawDate = transaction.date
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

    val transactionText = TransactionMode.values().firstOrNull() { it.ordinal == transaction.mode }?.text ?: ""

    var formattedDate = LocalDateTime.parse(rawDate.substring(0, 23), formatter)
        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
    // if date is today write today 16:30
    formattedDate = if(formattedDate.substring(0, 10) == LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))) {
        "Today " + formattedDate.substring(11, 16)
    } else {
        val monthName = monthDateConverter(LocalDate.parse(formattedDate.substring(0, 10), DateTimeFormatter.ofPattern("dd.MM.yyyy")))
        formattedDate.replaceRange(3..4, monthName).replace(".", " ")
    }

    val color = if (transaction.mode == TransactionMode.Send.ordinal || transaction.mode == TransactionMode.Withdraw.ordinal) {
        redColor
    } else {
        greenColor
    }

    LaunchedEffect(true) {
        liveData.getUsername(user) {
            if (it != null) {
                if(it.username == liveData.username.value){
                    username.value = "You"
                }
                else username.value = it.username
            } else {
                username.value = "Unknown"
            }
        }
    }

    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 10.dp)
            .border(2.dp, Color(0x110069a5), shape = MaterialTheme.shapes.medium)
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
                    .fillMaxWidth(0.6f),
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
                        text = "Transaction: $transactionText",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = username.value,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(700)),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    if(transaction.receiverPhone != transaction.senderPhone) {
                        Text(
                            text = "(${user})",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.alpha(0.7f),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    } else {
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
            Column() {
                Text(
//                text = (if (transaction.mode == TransactionMode.Send.ordinal) "" else "") + transaction.amount.toString() + " TL",
                    text = transaction.amount.toString().replace("-", "") + " TL",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(700)),
                    color = color,
                    modifier = Modifier.align(Alignment.End),
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(700), fontSize = 11.sp),
                    modifier = Modifier
                        .alpha(0.7f)
                        .align(Alignment.End),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}