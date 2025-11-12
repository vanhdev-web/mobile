package com.example.buoi_6

class SinhVien(
    var maSinhVien: String = "",
    var ho: String = "",
    var ten: String = "",
    var maLop: String = ""
) {
    companion object {
        const val TABLE_NAME = "SinhVien"
        const val COLUMN_MASINHVIEN = "maSinhVien"
        const val COLUMN_HO = "ho"
        const val COLUMN_TEN = "ten"
        const val COLUMN_MALOP = "maLop"
    }
}

