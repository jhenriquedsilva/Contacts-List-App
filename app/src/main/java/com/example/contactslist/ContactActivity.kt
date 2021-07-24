package com.example.contactslist

import android.os.Bundle
import android.view.View
import com.example.contactslist.application.ContactApplication

class ContactActivity : BaseActivity() {

    private var contactID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        setupToolBar(toolBar, "Contact", true)
        setupContact()
        saveContactButton.setOnClickListener { onClickSaveContact() }
    }

    private fun setupContact() {
        contactID = intent.getIntExtra("index", -1)
        if (contactID == -1) {
            deleteContactButton.visibility = View.GONE
            return
        }
        var list = ContactApplication.instance.helperDB?.searchContact("$contactID", true) ?: return
        var contact = list.getOrNull(0) ?: return
        editTextName.setText(contact.name)
        editTextPhoneNumber.setText(contact.phoneNumber)
    }

    private fun onClickSaveContact() {
        val name = editTextName.text.toString()
        val phoneNumber = editPhoneNUmber.text.toString()
        val contact = ContactsVO(contactID,name,phoneNumber)

        if (contactID == -1) {
            ContactApplication.instance.helperDB?.saveContact(contact)
        } else {
            ContactApplication.instance.helperDB?.updateContact(contact)
        }

        finish()
    }

    fun onCLickRemoveContact(view: View) {
        if (contactID > -1) {
            ContactApplication.instance.helperDB?.deleteCOntact(contactID)
            finish()
        }
    }
}