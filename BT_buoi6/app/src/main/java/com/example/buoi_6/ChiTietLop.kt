package com.example.buoi_6

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buoi_6.databinding.ActivityChiTietLopBinding

class ChiTietLop : AppCompatActivity() {
    private lateinit var binding: ActivityChiTietLopBinding
    private lateinit var sqliteHelper: QLDTSQLiteHelper

    private lateinit var listLopHoc: ArrayList<Lop>
    private lateinit var listSinhVien: ArrayList<SinhVien>
    private lateinit var adapterSinhVien: ArrayAdapter<String>
    private lateinit var listSinhVienDisplay: MutableList<String>
    private var selectedMaLop: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            binding = ActivityChiTietLopBinding.inflate(layoutInflater)
            setContentView(binding.root)

            enableEdgeToEdge()
            ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            sqliteHelper = QLDTSQLiteHelper(this)
            loadSpinnerLop()
            addEvents()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Lỗi: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }


    private fun loadSpinnerLop() {
        listLopHoc = sqliteHelper.getAllLop()

        if (listLopHoc.isEmpty()) {
            Toast.makeText(this, "Chưa có lớp học nào. Vui lòng thêm lớp trước!", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val listLopDisplay = mutableListOf<String>()
        for (lop in listLopHoc) {
            listLopDisplay.add(lop.maLop + " - " + lop.tenLop)
        }

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listLopDisplay)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerAdapter

        selectedMaLop = listLopHoc[0].maLop
        loadSinhVienListView()
    }

    private fun addEvents() {
        binding.btnQuayVe.setOnClickListener {
            finish()
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedMaLop = listLopHoc[position].maLop
                loadSinhVienListView()
                clearInputs()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.btnThemSV.setOnClickListener {
            val maSinhVien = binding.edtMaSinhVien.text.toString().trim()
            val ho = binding.edtHo.text.toString().trim()
            val ten = binding.edtTen.text.toString().trim()

            if (maSinhVien.isEmpty() || ho.isEmpty() || ten.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedMaLop.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn lớp", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sinhVien = SinhVien(maSinhVien, ho, ten, selectedMaLop)
            sqliteHelper.insertSinhVien(sinhVien)
            Toast.makeText(this, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show()
            loadSinhVienListView()
            clearInputs()
        }

        binding.btnSuaSV.setOnClickListener {
            val maSinhVien = binding.edtMaSinhVien.text.toString().trim()
            val ho = binding.edtHo.text.toString().trim()
            val ten = binding.edtTen.text.toString().trim()

            if (maSinhVien.isEmpty() || ho.isEmpty() || ten.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedMaLop.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn lớp", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sinhVien = SinhVien(maSinhVien, ho, ten, selectedMaLop)
            sqliteHelper.updateSinhVien(sinhVien)
            Toast.makeText(this, "Cập nhật sinh viên thành công", Toast.LENGTH_SHORT).show()
            loadSinhVienListView()
            clearInputs()
        }

        binding.btnXoaSV.setOnClickListener {
            val maSinhVien = binding.edtMaSinhVien.text.toString().trim()

            if (maSinhVien.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn sinh viên cần xóa", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sqliteHelper.deleteSinhVien(maSinhVien)
            Toast.makeText(this, "Xóa sinh viên thành công", Toast.LENGTH_SHORT).show()
            loadSinhVienListView()
            clearInputs()
        }

        binding.btnLoadSV.setOnClickListener {
            loadSinhVienListView()
            clearInputs()
        }

        binding.lvSinhVien.setOnItemClickListener { parent, view, position, id ->
            val selectedSV = listSinhVien.firstOrNull { sv ->
                (sv.maSinhVien + " - " + sv.ho + " " + sv.ten) == listSinhVienDisplay[position]
            }
            selectedSV?.let {
                binding.edtMaSinhVien.setText(it.maSinhVien)
                binding.edtHo.setText(it.ho)
                binding.edtTen.setText(it.ten)
            }
        }

        // SearchView functionality
        binding.searchViewSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterSinhVienList(newText ?: "")
                return true
            }
        })
    }

    private fun loadSinhVienListView() {
        listSinhVien = sqliteHelper.getSinhVienByLop(selectedMaLop)
        listSinhVienDisplay = mutableListOf()

        for (sv in listSinhVien) {
            listSinhVienDisplay.add(sv.maSinhVien + " - " + sv.ho + " " + sv.ten)
        }

        adapterSinhVien = ArrayAdapter(this, android.R.layout.simple_list_item_1, listSinhVienDisplay)
        binding.lvSinhVien.adapter = adapterSinhVien
    }

    private fun clearInputs() {
        binding.edtMaSinhVien.setText("")
        binding.edtHo.setText("")
        binding.edtTen.setText("")
    }

    private fun filterSinhVienList(query: String) {
        listSinhVienDisplay.clear()

        if (query.isEmpty()) {
            for (sv in listSinhVien) {
                listSinhVienDisplay.add(sv.maSinhVien + " - " + sv.ho + " " + sv.ten)
            }
        } else {
            for (sv in listSinhVien) {
                if (sv.maSinhVien.contains(query, ignoreCase = true) ||
                    sv.ho.contains(query, ignoreCase = true) ||
                    sv.ten.contains(query, ignoreCase = true)) {
                    listSinhVienDisplay.add(sv.maSinhVien + " - " + sv.ho + " " + sv.ten)
                }
            }
        }

        adapterSinhVien.notifyDataSetChanged()
    }
}