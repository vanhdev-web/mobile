package com.example.buoi_6

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class CopyDBHelper (private val context: Context) {
    companion object {
        private const val DATABASE_NAME = "qlSinhVien.db"
        private const val DATABASE_VERSION = 1
    }
    fun openDatabase(): SQLiteDatabase {
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        val file = File(dbFile.toString())
        if (file.exists()) {
            Log.w("existFile", "File đã tồn tại")
        }
        else {
            copyDatabase(dbFile)
        }
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READONLY)
    }

    private fun copyDatabase(dbFile: File?) {
        val openDB = context.assets.open(DATABASE_NAME)
        val outputStream = FileOutputStream(dbFile)
        val buffer = ByteArray(1024)
        while (openDB.read(buffer) > 0) {
            outputStream.write(buffer)
            Log.i("writeDB", "Đang ghi từ Buffer vào outputStream")
        }
        outputStream.flush()
        outputStream.close()
        openDB.close()
        Log.i("copyDB", "Đã copy database")
    }

}