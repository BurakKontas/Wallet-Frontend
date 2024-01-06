package com.aburakkontas.wallet

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aburakkontas.wallet.classes.Contact

@SuppressLint("Range")
fun getAllContacts(context: Context, liveData: LiveData) {
    if (checkContactPermission(context)) {
        val list = mutableListOf<Contact>()
        val contentResolver: ContentResolver = context.contentResolver
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val contactIdIndex = cursor?.getColumnIndex(ContactsContract.Contacts._ID)
        val displayNameIndex = cursor?.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
        val phoneIndex = cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val contactId = if (contactIdIndex != -1) cursor.getString(contactIdIndex!!) else ""
                val displayName = if (displayNameIndex != -1) cursor.getString(displayNameIndex!!) else ""

                var phone = getContactNumbers(contentResolver, contactId)
                phone = phone.replace(" ", "").replace("-", "").replace("(", "").replace(")", "")
                Log.d("Contact", "ID: $contactId, Name: $displayName, Phone: $phone")

                val contact = Contact(contactId!!, displayName!!, phone)
                list.add(contact)
            }
            cursor.close()
            liveData.contacts.value = list.sortedBy { it.name }
        }
    } else {
        requestContactPermission(context)
    }
}

@SuppressLint("Range")
private fun getContactNumbers(contentResolver: ContentResolver, contactId: String): String {
    var phoneNumber: String = ""
    val cursor: Cursor? = contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
        arrayOf(contactId),
        null
    )

    cursor?.let {
        while (cursor.moveToNext()) {
            phoneNumber =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            Log.d("Contact", "Phone Number: $phoneNumber")
        }
        cursor.close()
    }
    return phoneNumber
}

private fun checkContactPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_CONTACTS
    ) == PackageManager.PERMISSION_GRANTED
}

private fun requestContactPermission(context: Context) {
    ActivityCompat.requestPermissions(
        context as Activity,
        arrayOf(Manifest.permission.READ_CONTACTS),
        CONTACT_PERMISSION_REQUEST_CODE
    )
}

private const val CONTACT_PERMISSION_REQUEST_CODE = 123
