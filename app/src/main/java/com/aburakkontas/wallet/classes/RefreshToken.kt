package com.aburakkontas.wallet.classes

data class RefreshTokenData(
    val refreshToken: String
)

data class RefreshTokenResponse(
    val token: String,
    val phone: String,
    val refreshToken: String,
    val username: String
)