package com.aburakkontas.wallet

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.aburakkontas.wallet.classes.Contact
import com.aburakkontas.wallet.classes.GetUsernameDataResponse
import com.aburakkontas.wallet.classes.Language
import com.aburakkontas.wallet.classes.Transaction
import com.aburakkontas.wallet.enums.TransactionMode
import com.aburakkontas.wallet.services.AuthService
import com.aburakkontas.wallet.services.LocalStorage
import com.aburakkontas.wallet.services.TransactionsService
import com.aburakkontas.wallet.services.UsersService
import com.aburakkontas.wallet.services.WalletService
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class LiveData(private val context: Context, private val navController: NavController) : ViewModel() {

    private val localStorage = LocalStorage.getInstance(context)
    private val authService = AuthService()
    private val transactionsService = TransactionsService()
    private val walletService = WalletService()
    private val usersService = UsersService()

    private val token = MutableLiveData<String>("")
    private val refreshToken = MutableLiveData<String>("")

    val phone = MutableLiveData<String>("")
    val username = MutableLiveData<String>("")
    val balance = MutableLiveData<Double>(0.0)
    val contacts = MutableLiveData<List<Contact>>(listOf())
    val transactions = MutableLiveData<List<Transaction>>(listOf())
    val language = MutableLiveData<String>("en-US")
    val languageData = MutableLiveData<Language>()

    fun navigate(route: String) {
        navController.navigate(route)
    }

    fun login(username: String, password: String, remember_me: Boolean = false) {
        viewModelScope.launch {
            try {
                val result = authService.login(username, password)
                this@LiveData.token.value = result.token
                this@LiveData.refreshToken.value = result.refreshToken
                this@LiveData.username.value = result.username
                this@LiveData.phone.value = result.phone
                if(remember_me) localStorage.saveData("refreshToken", result.refreshToken)
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                navController.navigate("home")
            } catch (e: Exception) {
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            localStorage.removeData("refreshToken")
            Toast.makeText(context, "Logout successful", Toast.LENGTH_SHORT).show()
            navController.navigate("login")
        }
    }

    fun register(phone: String, username: String, password: String, remember_me: Boolean = false) {
        viewModelScope.launch {
            try {
                val result = authService.register(phone, username, password)
                this@LiveData.token.value = result.token
                this@LiveData.refreshToken.value = result.refreshToken
                this@LiveData.username.value = result.username
                this@LiveData.phone.value = result.phone
                if(remember_me) localStorage.saveData("refreshToken", result.refreshToken)
                Toast.makeText(context, "Register successful", Toast.LENGTH_SHORT).show()
                navController.navigate("home")
            } catch (e: Exception) {
                Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    fun resetPassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            try {
                val result = authService.resetPassword(token.value!!, currentPassword, newPassword)
                if(result) Toast.makeText(context, "Password changed", Toast.LENGTH_SHORT).show()
                else Toast.makeText(context, "Password change failed", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Password change failed", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    fun refreshToken() {
        viewModelScope.launch {
            try {
                val refreshToken = localStorage.getData("refreshToken", "")
                if (refreshToken != "") {
                    val result = authService.refreshToken(refreshToken)
                    this@LiveData.token.value = result.token
                    this@LiveData.refreshToken.value = result.refreshToken
                    this@LiveData.username.value = result.username
                    this@LiveData.phone.value = result.phone
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    navController.navigate("home")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Refresh token failed", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    fun getTransactions(limit: Int, mode: TransactionMode = TransactionMode.All) {
        viewModelScope.launch {
            try {
                val result = transactionsService.getTransactions(token.value!!, limit, mode)
                if (result != null) {
                    this@LiveData.transactions.value = result.transactions
                }
            } catch (e: Exception) {
                // Handle exception
                e.printStackTrace()
            }
        }
    }

    fun deposit(amount: Double) {
        viewModelScope.launch {
            try {
                val result = transactionsService.deposit(token.value!!, amount)
                if(result) {
                    Toast.makeText(context, "Deposit successful", Toast.LENGTH_SHORT).show()
                    navController.navigate("home")
                } else {
                    Toast.makeText(context, "Deposit failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Deposit failed", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    fun withdraw(amount: Double) {
        viewModelScope.launch {
            try {
                val result = transactionsService.withdraw(token.value!!, amount)
                if(result) {
                    Toast.makeText(context, "Withdraw successful", Toast.LENGTH_SHORT).show()
                    navController.navigate("home")
                } else {
                    Toast.makeText(context, "Withdraw failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Withdraw failed", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    fun updateBalance() {
        viewModelScope.launch {
            try {
                val result = walletService.checkBalance(token.value!!)
                balance.value = result.balance
            } catch (e: Exception) {
                // Handle exception
                e.printStackTrace()
            }
        }
    }

    fun checkContacts() {
        viewModelScope.launch {
            try {
                getAllContacts(context, this@LiveData)
                val contactPhoneList = contacts.value!!.map { it.phone }
                val result = usersService.checkContacts(token.value!!, contactPhoneList)
                contacts.value = contacts.value!!.filter { contact -> (contact.phone in result.contacts) && contact.phone != phone.value!! }
                for(contact in contacts.value!!) {
                    usersService.getUserUsername(token.value!!, contact.phone) { username ->
                        if(username != null) contact.name = "${username.username} (${contact.phone})"
                        else contact.name = "Unknown (${contact.phone})"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sendMoney(recipientPhone: String, amount: Double) {
        viewModelScope.launch {
            try {
                val result = transactionsService.sendMoney(token.value!!, recipientPhone, amount)
                if(result) {
                    Toast.makeText(context, "Money sent", Toast.LENGTH_SHORT).show()
                    navController.navigate("home")
                } else {
                    Toast.makeText(context, "Error sending money", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error sending money", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    fun getUsername(phone: String, onResult: (GetUsernameDataResponse?) -> Unit) {
        usersService.getUserUsername(token.value!!, phone, onResult)
    }
}
