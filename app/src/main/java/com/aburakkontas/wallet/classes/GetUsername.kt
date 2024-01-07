package com.aburakkontas.wallet.classes

data class GetUsernameData(
    val phone: String
)

data class GetUsernameDataResponse(
    val username: String
)