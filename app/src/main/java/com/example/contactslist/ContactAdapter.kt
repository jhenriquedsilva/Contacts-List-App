package com.example.contactslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    private val context: Context,
    private val list: List<Contacts>,
    private val onClick: ((Int) -> Unit)
): RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(/* I'll create another layout and put here */,parent,false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = list[position]
        with(holder.itemView) {
            nameTextView.text = contact.name
            phoneNumberTextView.text = contact.phoneNumber
            itemLinearLayout.setOnClickListener { onClick(contact.id) }
        }
    }
}