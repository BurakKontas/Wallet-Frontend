package com.aburakkontas.wallet.classes

import org.json.JSONObject

data class Language(
    val homepage: String = "",
    val send_money: String = "",
    val transactions: String = "",
    val settings: String = "",
    val balance: String = "",
    val deposit: String = "",
    val withdraw: String = "",
    val received: String = "",
    val sent: String = "",
    val selectAContact: String = "",
    val amount: String = "",
    val send: String = "",
    val language: String = "",
    val username: String = "",
    val phone: String = "",
    val currentPassword: String = "",
    val newPassword: String = "",
    val changePassword: String = "",
    val logout: String = "",
    val successfull: String = "",
    val failed: String = "",
    val login: String = "",
    val phone_number: String = "",
    val password: String = "",
    val remember_me: String = "",
    val register: String = "",
    val money: String = "",
    val sending_error: String = "",
) {
    operator fun get(transactionText: String): Any {
        return when (transactionText) {
            "Homepage" -> homepage
            "Sendmoney" -> send_money
            "Transactions" -> transactions
            "Settings" -> settings
            "Balance" -> balance
            "Deposit" -> deposit
            "Withdraw" -> withdraw
            "Received" -> received
            "Sent" -> sent
            "Select_a_contact" -> selectAContact
            "Amount" -> amount
            "Send" -> send
            "Language" -> language
            "Username" -> username
            "Phone" -> phone
            "Current_Password" -> currentPassword
            "New_Password" -> newPassword
            "Change_Password" -> changePassword
            "Logout" -> logout
            "Successfull" -> successfull
            "Failed" -> failed
            "Login" -> login
            "Phone_number" -> phone_number
            "Password" -> password
            "Remember_me" -> remember_me
            "Register" -> register
            "Money" -> money
            "Sending_error" -> sending_error
            else -> ""
        }
    }

    companion object {
        fun fromJson(json: JSONObject): Language {
            return Language(
                homepage = json.getString("Homepage"),
                send_money = json.getString("Sendmoney"),
                transactions = json.getString("Transactions"),
                settings = json.getString("Settings"),
                balance = json.getString("Balance"),
                deposit = json.getString("Deposit"),
                withdraw = json.getString("Withdraw"),
                received = json.getString("Received"),
                sent = json.getString("Sent"),
                selectAContact = json.getString("Select_a_contact"),
                amount = json.getString("Amount"),
                send = json.getString("Send"),
                language = json.getString("Language"),
                username = json.getString("Username"),
                phone = json.getString("Phone"),
                currentPassword = json.getString("Current_Password"),
                newPassword = json.getString("New_Password"),
                changePassword = json.getString("Change_Password"),
                logout = json.getString("Logout"),
                successfull = json.getString("Successfull"),
                failed = json.getString("Failed"),
                login = json.getString("Login"),
                phone_number = json.getString("Phone_number"),
                password = json.getString("Password"),
                remember_me = json.getString("Remember_me"),
                register = json.getString("Register"),
                money = json.getString("Money"),
                sending_error = json.getString("Sending_error"),
            )
        }
    }
}
