package com.aburakkontas.wallet.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aburakkontas.wallet.LiveData
import com.aburakkontas.wallet.classes.Contact
import com.aburakkontas.wallet.classes.SendMoneyData
import com.aburakkontas.wallet.components.Logo
import com.aburakkontas.wallet.getAllContacts
import com.aburakkontas.wallet.services.TransactionsService
import com.aburakkontas.wallet.services.UsersService

@Composable
fun Send(liveData: LiveData, navController: NavController) {
    val context = LocalContext.current
    val usersService = remember { UsersService() }
    val transactionsService = remember { TransactionsService() }

    LaunchedEffect(true) {
        getAllContacts(context, liveData)
        usersService.checkContacts(liveData.token.value!!, liveData.contacts.value!!.map { it.phone }) {
            if (it != null) {
                liveData.contacts.value = liveData.contacts.value!!.filter { contact -> (contact.phone in it.contacts) && contact.phone != liveData.phone.value!! }
            }
        }
    }

    var selectedContact by remember { mutableStateOf<Contact?>(null) }
    var amount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        ContactDropdownMenu(
            contacts = liveData.contacts.value!!,
            onContactSelected = { selectedContact = it }
        )

        // Amount field
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (selectedContact != null && amount.isNotEmpty()) {
                    val token = liveData.token.value!!
                    transactionsService.sendMoney(token, selectedContact!!.phone, amount.toDouble()) {
                        if (it != null) {
                            Log.d("Send", it.toString())
                            Toast.makeText(context, "Money sent", Toast.LENGTH_SHORT).show()
                            navController.navigate("home")
                        } else {
                            Toast.makeText(context, "Error sending money", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send")
        }
    }
}

@Composable
fun ContactDropdownMenu(
    contacts: List<Contact>,
    onContactSelected: (Contact) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedContact by remember { mutableStateOf<Contact?>(null) }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp)
    ) {
        Text(
            text = selectedContact?.name ?: "Select a contact",
            modifier = Modifier
                .clickable { expanded = true }
                .padding(16.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .padding(8.dp)
                .background(Color.White)
                .fillMaxWidth()
                .height(48.dp)
                .padding(16.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            contacts.forEach { contact ->
                DropdownMenuItem(
                    onClick = {
                        selectedContact = contact
                        expanded = false
                        onContactSelected(contact)
                    },
                    text = {
                        Text(
                            text = contact.name,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                    )},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )}
            }
        }
}

