package com.example.contactslist.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperDB(
    context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME, null, CURRENT_VERSION) {

    companion object {
        private val DATABASE_NAME = "contact.db"
        private val CURRENT_VERSION = 2
    }

    val TABLE_NAME = "contact"
    val COLUMNS_ID = "id"
    val COLUMNS_NAME = "namae"
    val COLUMNS_PHONE_NUMBER = "phone_number"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMNS_ID INTEGER NOT NULL," +
            "$COLUMNS_NAME TEXT NOT NULL," +
            "$COLUMNS_PHONE_NUMBER TEXT NOT NULL," +
            "PRIMARY KEY($COLUMNS_ID AUTOINCREMENT)" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL(DROP_TABLE)
        }
        onCreate(db)
    }

    fun searchContact(search: String, isSearchByID: Boolean = false):List<ContactVO> {

        val db = readableDatabase ?: return mutableListOf()
        var list = mutableListOf<ContactVO>()
        var where: String? = null
        var args: Array<String> = arrayOf()

        if (isSearchByID) {
            where = "$COLUMNS_ID = ?"
            args = arrayOf("$search")
        } else {
            where = "$COLUMNS_NAME = ?"
            args = arrayOf("%$search%")
        }


        if (cursor == null) {
            db.close()
            return mutableListOf()
        }
        while (cursor.moveToNext()) {
            var contact = ContactVO(
                cursor.getInt(cursor.getColumnIndex(COLUMNS_ID))),
                cursor.getString(cursor.getColumnIndex(COLUMNS_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_PHONE_NUMBER))
            )
            list.add(contact)
        }
        db.close()
        return list
    }

    fun saveContact(contact: ContactVO) {
        val db = writableDatabase ?: return
        val sql = "INSERT INTO $TABLE_NAME ($COLUMNS_NAME, $COLUMNS_PHONE_NUMBER) VALUES (?,?)"
        var array = arrayOf(contact.name, contact.phoneNumber)
        db.execSQL(sql, array)
        db.close()
    }

    fun deleteContact(id: Int) {
        val db = writableDatabase ?: return
        val where = "id - ?"
        val arg = arrayOf("$id")
        db.delete(TABLE_NAME, where, arg)
        db.close()
    }

    fun updateContact(contact: ContactVO) {
        val db = writableDatabase ?: return
        val content = ContentValues()
        content.put(COLUMNS_NAME, contact.name)
        content.put(COLUMNS_PHONE_NUMBER, contact.phoneNumber)
        val where = "id = ?"
        var arg = arrayOf("${contact.id}")
        db.update(TABLE_NAME, content, where, arg)
        db.close()
    }

}