package com.aburakkontas.wallet.enums

enum class TransactionMode(val value: Int, val text: String) {
    Empty(0, ""),
    Send(1, "Send"),
    Receive(2, "Receive"),
    Withdraw(3, "Withdraw"),
    Deposit(4, "Deposit"),
    All(5, "All")
}