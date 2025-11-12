package com.example.buoi_6

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buoi_6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var sqliteHelper: QLDTSQLiteHelper
    private lateinit var listLopHoc: ArrayList<Lop>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var listLopDisplay: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initQLDTDatabase()
        loadLopListView()
        addEvents()
    }

    private fun addEvents() {
        binding.btnThem.setOnClickListener {
            val maLop = binding.edtMaLop.text.toString().trim()
            val tenLop = binding.edtTenLop.text.toString().trim()

            if (maLop.isEmpty() || tenLop.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sqliteHelper.insertLop(Lop(maLop, tenLop))
            Toast.makeText(this, "Thêm lớp thành công", Toast.LENGTH_SHORT).show()
            loadLopListView()
            clearInputs()
        }

        binding.btnXoa.setOnClickListener {
            val maLop = binding.edtMaLop.text.toString().trim()

            if (maLop.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn lớp cần xóa", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sqliteHelper.deleteLop(maLop)
            Toast.makeText(this, "Xóa lớp thành công", Toast.LENGTH_SHORT).show()
            loadLopListView()
            clearInputs()
        }

        binding.btnSua.setOnClickListener {
            val maLop = binding.edtMaLop.text.toString().trim()
            val tenLop = binding.edtTenLop.text.toString().trim()

            if (maLop.isEmpty() || tenLop.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sqliteHelper.updateLop(Lop(maLop, tenLop))
            Toast.makeText(this, "Cập nhật lớp thành công", Toast.LENGTH_SHORT).show()
            loadLopListView()
            clearInputs()
        }

        binding.btnLoad.setOnClickListener {
            loadLopListView()
            clearInputs()
        }

        binding.btnXemChiTiet.setOnClickListener {
            val intent = Intent(this, ChiTietLop::class.java)
            startActivity(intent)
        }

        binding.lvLop.setOnItemClickListener { parent, view, position, id ->
            val selectedLop = listLopHoc.firstOrNull { lop ->
                (lop.maLop + " - " + lop.tenLop) == listLopDisplay[position]
            }
            selectedLop?.let {
                binding.edtMaLop.setText(it.maLop)
                binding.edtTenLop.setText(it.tenLop)
            }
        }

        // SearchView functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterLopList(newText ?: "")
                return true
            }
        })
    }

    private fun clearInputs() {
        binding.edtMaLop.setText("")
        binding.edtTenLop.setText("")
    }

    private fun filterLopList(query: String) {
        listLopDisplay.clear()

        if (query.isEmpty()) {
            for (lop in listLopHoc) {
                listLopDisplay.add(lop.maLop + " - " + lop.tenLop)
            }
        } else {
            for (lop in listLopHoc) {
                if (lop.maLop.contains(query, ignoreCase = true) ||
                    lop.tenLop.contains(query, ignoreCase = true)) {
                    listLopDisplay.add(lop.maLop + " - " + lop.tenLop)
                }
            }
        }

        adapter.notifyDataSetChanged()
    }

    private fun loadLopListView() {
        listLopHoc = sqliteHelper.getAllLop()
        listLopDisplay = mutableListOf()

        for (lop in listLopHoc) {
            listLopDisplay.add(lop.maLop + " - " + lop.tenLop)
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listLopDisplay)
        binding.lvLop.adapter = adapter
    }

    private fun initQLDTDatabase() {
        sqliteHelper = QLDTSQLiteHelper(this)
    }
}