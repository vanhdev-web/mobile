package com.example.buoi_6

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QLDTSQLiteHelper(context: Context): SQLiteOpenHelper(context, MyVariable.DB_NAME, null, MyVariable.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Create Lop table
        val queryLop = String.format(
            "CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT)",
            Lop.TABLE_NAME,
            Lop.COLUMN_MALOP,
            Lop.COLUMN_TENLOP
        )
        db!!.execSQL(queryLop)

        // Create SinhVien table (without Foreign Key constraint for simplicity)
        val querySinhVien = String.format(
            "CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)",
            SinhVien.TABLE_NAME,
            SinhVien.COLUMN_MASINHVIEN,
            SinhVien.COLUMN_HO,
            SinhVien.COLUMN_TEN,
            SinhVien.COLUMN_MALOP
        )
        db.execSQL(querySinhVien)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS " + SinhVien.TABLE_NAME)
        db?.execSQL("DROP TABLE IF EXISTS " + Lop.TABLE_NAME)
        onCreate(db)
    }

    fun insertLop(lop: Lop): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Lop.COLUMN_MALOP, lop.maLop)
        values.put(Lop.COLUMN_TENLOP, lop.tenLop)
        val id = db.insert(Lop.TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun updateLop(lop: Lop) : Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Lop.COLUMN_MALOP, lop.maLop)
        values.put(Lop.COLUMN_TENLOP, lop.tenLop)

        val result = db.update(Lop.TABLE_NAME, values, Lop.COLUMN_MALOP + " = '" + lop.maLop + "'", null)

        db.close()
        return result
    }

    fun deleteLop(maLop: String) {
        val db = this.writableDatabase
        db.delete(Lop.TABLE_NAME, Lop.COLUMN_MALOP + " = '" + maLop + "'", null)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllLop(): ArrayList<Lop> {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM " + Lop.TABLE_NAME, null)
        val lopList : ArrayList<Lop> = ArrayList()

        if (cursor.moveToFirst()) {
            do {
                val lop = Lop(
                    cursor.getString(cursor.getColumnIndex(Lop.COLUMN_MALOP)),
                    cursor.getString(cursor.getColumnIndex(Lop.COLUMN_TENLOP))
                )
                lopList.add(lop)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lopList
    }

    // CRUD operations for SinhVien
    fun insertSinhVien(sinhVien: SinhVien): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(SinhVien.COLUMN_MASINHVIEN, sinhVien.maSinhVien)
        values.put(SinhVien.COLUMN_HO, sinhVien.ho)
        values.put(SinhVien.COLUMN_TEN, sinhVien.ten)
        values.put(SinhVien.COLUMN_MALOP, sinhVien.maLop)
        val id = db.insert(SinhVien.TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun updateSinhVien(sinhVien: SinhVien): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(SinhVien.COLUMN_MASINHVIEN, sinhVien.maSinhVien)
        values.put(SinhVien.COLUMN_HO, sinhVien.ho)
        values.put(SinhVien.COLUMN_TEN, sinhVien.ten)
        values.put(SinhVien.COLUMN_MALOP, sinhVien.maLop)
        val result = db.update(SinhVien.TABLE_NAME, values, SinhVien.COLUMN_MASINHVIEN + " = ?", arrayOf(sinhVien.maSinhVien))
        db.close()
        return result
    }

    fun deleteSinhVien(maSinhVien: String) {
        val db = this.writableDatabase
        db.delete(SinhVien.TABLE_NAME, SinhVien.COLUMN_MASINHVIEN + " = ?", arrayOf(maSinhVien))
        db.close()
    }

    @SuppressLint("Range")
    fun getAllSinhVien(): ArrayList<SinhVien> {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM " + SinhVien.TABLE_NAME, null)
        val sinhVienList: ArrayList<SinhVien> = ArrayList()

        if (cursor.moveToFirst()) {
            do {
                val sinhVien = SinhVien(
                    cursor.getString(cursor.getColumnIndex(SinhVien.COLUMN_MASINHVIEN)),
                    cursor.getString(cursor.getColumnIndex(SinhVien.COLUMN_HO)),
                    cursor.getString(cursor.getColumnIndex(SinhVien.COLUMN_TEN)),
                    cursor.getString(cursor.getColumnIndex(SinhVien.COLUMN_MALOP))
                )
                sinhVienList.add(sinhVien)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return sinhVienList
    }

    @SuppressLint("Range")
    fun getSinhVienByLop(maLop: String): ArrayList<SinhVien> {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM " + SinhVien.TABLE_NAME + " WHERE " + SinhVien.COLUMN_MALOP + " = ?", arrayOf(maLop))
        val sinhVienList: ArrayList<SinhVien> = ArrayList()

        if (cursor.moveToFirst()) {
            do {
                val sinhVien = SinhVien(
                    cursor.getString(cursor.getColumnIndex(SinhVien.COLUMN_MASINHVIEN)),
                    cursor.getString(cursor.getColumnIndex(SinhVien.COLUMN_HO)),
                    cursor.getString(cursor.getColumnIndex(SinhVien.COLUMN_TEN)),
                    cursor.getString(cursor.getColumnIndex(SinhVien.COLUMN_MALOP))
                )
                sinhVienList.add(sinhVien)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return sinhVienList
    }
}