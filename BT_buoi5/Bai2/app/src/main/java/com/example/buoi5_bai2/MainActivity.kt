package com.example.buoi5_bai2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buoi5_bai2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val fragment1 = Fragment1()
    private val fragment2 = Fragment2()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val list = listOf("Fragment 1", "Fragment 2")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        initMainActivity()
        addEvent()

    }
    private fun addEvent () {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.frame_container, fragment1)
                            commit()
                        }
                    }

                    else -> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.frame_container, fragment2)
                            commit()
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }
    private fun initMainActivity() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, fragment1)
            commit()
        }
    }
}