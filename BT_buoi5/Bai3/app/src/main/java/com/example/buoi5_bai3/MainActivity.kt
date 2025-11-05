package com.example.buoi5_bai3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buoi5_bai3.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private val fragment1 = Fragment1()
    private val fragment2 = Fragment2()
    private val fragment3 = Fragment3()

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

        initMainActivity()
        addEvent()

    }
    private fun addEvent () {
        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.fragmentContainerView, fragment1)
                            commit()
                        }
                    }
                    1 -> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.fragmentContainerView, fragment2)
                            commit()
                        }
                    }
                    2 -> {
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.fragmentContainerView, fragment3)
                            commit()
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Do nothing
            }
        })
    }

    private fun initMainActivity() {
        // Set the initial fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment1)
            commit()
        }
    }
}