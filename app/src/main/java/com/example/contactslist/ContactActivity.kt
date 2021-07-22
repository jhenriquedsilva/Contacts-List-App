package com.example.contactslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ContactActivity : BaseActivity() {

    private var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        setupToolBar(toolBar, "Contact", true)
        setupContact()
        saveContactButton.setOnClickListener { onClickSaveContact() }
    }

    private fun setupContact() {
        index = intent.getIntExtra("index", -1)
        if (index == -1) {
            deleteContactButton.visibility = View.GONE
            return
        }
        editTextName.setText(ContactSingleton.list[index].name)
        editTextPhoneNumber.setText(ContactSingleton.list[index].phoneNumber)
    }

    private fun onClickSaveContact() {
        val name = editTextName.text.toString()
        val phoneNumber = editPhoneNUmber.text.toString()
        val contact = ContactsVO(0,name,phoneNumber)

        if (index == -1) {
            ContactSingleton.list.add(contact)
        } else {
            ContactSingleton.list.set(index,contact)
        }

        finish()
    }

    fun onCLickRemoveContact(view: View) {
        if (index > -1) {
            ContactSingleton.list.removeAt(index)
            finish()
        }
    }
}