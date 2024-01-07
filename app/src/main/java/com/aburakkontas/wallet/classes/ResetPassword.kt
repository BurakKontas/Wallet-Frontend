package com.aburakkontas.wallet.classes

data class ResetPasswordData(
    val currentPassword: String,
    val password: String
)