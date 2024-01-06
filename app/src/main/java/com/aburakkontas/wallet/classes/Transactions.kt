package com.aburakkontas.wallet.classes

data class TransactionsData (
    val limit: Int,
    val mode: Int
)

data class Transaction (
    val senderPhone: String,
    val recipientPhone: String,
    val amount: Int,
    val date: String
)
data class TransactionsDataResponse (
    val transactions: List<Transaction>
)