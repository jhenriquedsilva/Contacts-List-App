package com.example.contactslist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactslist.application.ContactApplication
import com.example.contactslist.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ContactAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createContactsList()
        setupToolBar(toolBar, "Contacts List", false)
        setupRecyclerView()
        setupOnClicks()
    }

    private fun setupOnClicks() {
        binding.fab.setOnClickListener { onClickAdd() }
        binding.ivSearch.setOnClickListener { onClickSearch() }
    }

    private fun setupRecyclerView() {
        // I suppose that 'binding' is missing here
        recyclerView.layourManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        onClickSearch()
    }

    private fun onClickAdd() {
        val intent = Intent(this, ContactActivity::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int) {
        val intent = Intent(this, ContactActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun onClickSearch() {
        val search = etSearch.text.toString()

        Thread(Runnable {
            var filteredList: List<ContactVO> = mutableListOf()
            try {
                filteredList = ContactApplication.instance.helperDB?.searchContact(search) ?: mutableListOf()
            } catch(ex: Exception){
                ex.printStackTrace()
            }

            runOnUiThread {
                adapter = ContactAdapter(this, filteredList) { onClickItemRecyclerView(it) }
                recylerView.adapter = adapter
                Toast.makeText(this,"Searching for $search",Toast.LENGTH_SHORT).show()
            }

        }).start()

    }
}