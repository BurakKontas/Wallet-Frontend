package com.aburakkontas.wallet.classes

data class RegisterData(
    val phone: String,
    val password: String,
    val username: String
)

data class RegisterDataResponse(
    val token: String,
    val phone: String,
    val refreshToken: String,
    val username: String
)