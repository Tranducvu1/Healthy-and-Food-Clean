package com.example.healthyandfoodclean

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
//tao database ung dung
class MyDatabaseHelper(context: Context, s: String, nothing: Nothing?, i: Int) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        // Tên bảng và tên cột
        private const val DATABASE_NAME = "HealthyAndFoodClean.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Data"
        private const val COLUMN_ID = "id"
        private const val COLUMN_PERSON_NAME = "personname"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_REPASSWORD = "repassword"
        private const val COLUMN_PHONE_NUMBER = "phonenumber"
    }
    // Tạo bảng
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME"
                + "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_PERSON_NAME TEXT,"
                + "$COLUMN_PASSWORD TEXT,"
                + "$COLUMN_REPASSWORD TEXT,"
                + "$COLUMN_PHONE_NUMBER TEXT)")
        db?.execSQL(CREATE_TABLE_QUERY)
    }
    // Xoá bảng nếu nó tồn tại và tạo một bảng mới
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    // them du lieu
    fun addData(data: Data) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PERSON_NAME, data.personname)
            put(COLUMN_PASSWORD, data.password)
            put(COLUMN_REPASSWORD, data.repassword)
            put(COLUMN_PHONE_NUMBER, data.phone)

        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun register(personname: String, password: String, repassword: String, phone: String) {
        val values = ContentValues().apply {
            put(COLUMN_PERSON_NAME, personname)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_REPASSWORD, repassword)
            put(COLUMN_PHONE_NUMBER, phone)
        }

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun login(personname: String, password: String): Int {
        val db = readableDatabase
        var result = 0
        val selection = "$COLUMN_PERSON_NAME=? AND $COLUMN_PASSWORD=?"
        val selectionArgs = arrayOf(personname, password)
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)
        if (cursor.moveToFirst()) {
            result = 1
        }
        cursor.close()
        db.close()
        return result
    }
}