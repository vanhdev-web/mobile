package com.example.buoi4_bai3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buoi4_bai3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dogs: List<Dog>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dogs = getDummyDogs()

        val adapter = DogAdapter(this, dogs)
        binding.gridView.adapter = adapter

        binding.gridView.setOnItemClickListener { _, _, position, _ ->
            val selectedDog = dogs[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("dog", selectedDog)
            startActivity(intent)
        }
    }

    private fun getDummyDogs(): List<Dog> {
        return listOf(
            Dog(1, "Milo", R.drawable.dog1),
            Dog(2, "Luna", R.drawable.dog2),
            Dog(3, "Charlie", R.drawable.dog3),
            Dog(4, "Lucy", R.drawable.dog1),
            Dog(5, "Cooper", R.drawable.dog2),
            Dog(6, "Daisy", R.drawable.dog3),
            Dog(7, "Max", R.drawable.dog1),
            Dog(8, "Bella", R.drawable.dog2),
            Dog(9, "Rocky", R.drawable.dog3),
            Dog(10, "Sadie", R.drawable.dog1),
            Dog(11, "Buddy", R.drawable.dog2),
            Dog(12, "Molly", R.drawable.dog3)
        )
    }
}
