package com.aburakkontas.wallet.classes

import com.aburakkontas.wallet.enums.TransactionMode

data class TransactionsData (
    val limit: Int,
    val mode: Int
)

data class Transaction (
    val senderPhone: String,
    val receiverPhone: String,
    val amount: Double,
    val date: String,
    val mode: Int
)
data class TransactionsDataResponse (
    val transactions: List<Transaction>
)