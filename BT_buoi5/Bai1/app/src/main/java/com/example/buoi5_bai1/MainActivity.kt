package com.example.buoi5_bai1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val list = ArrayList<CustomSpinnerModel>()
        list.add(CustomSpinnerModel(R.drawable.bmw, "BMW"))
        list.add(CustomSpinnerModel(R.drawable.mercedes, "Mercedes"))
        list.add(CustomSpinnerModel(R.drawable.honda, "Honda"))
        list.add(CustomSpinnerModel(R.drawable.porsche, "Porsche"))
        list.add(CustomSpinnerModel(R.drawable.toyota, "Toyota"))
        list.add(CustomSpinnerModel(R.drawable.cadillac, "Cadillac"))
        list.add(CustomSpinnerModel(R.drawable.chevrolet, "Chevrolet"))

        val adapter = CustomSpinnerAdapter(this, list)
        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedBrand = list[position]
                Toast.makeText(this@MainActivity, "Bạn đã chọn: ${selectedBrand.name}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Không cần xử lý
            }
        }
    }
}