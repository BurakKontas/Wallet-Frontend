package com.aburakkontas.wallet.classes

data class LoginData (
    val phone: String,
    val password: String
)

data class LoginResponse (
    val phone: String,
    val token: String,
    val refreshToken: String,
    val username: String
)