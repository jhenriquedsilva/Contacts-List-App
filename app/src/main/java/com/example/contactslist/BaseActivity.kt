package com.example.contactslist

import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    protected fun setupToolBar(toolBar: Toolbar, title:String, navigationBack: Boolean) {
        toolBar.title = title
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(navigationBack)
    }

    // When the toolbar is pressed, this function is called, passing in the item that was clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.home -> { this.onBackPressed(); return true }
        }
        return super.onOptionsItemSelected(item)
    }

}