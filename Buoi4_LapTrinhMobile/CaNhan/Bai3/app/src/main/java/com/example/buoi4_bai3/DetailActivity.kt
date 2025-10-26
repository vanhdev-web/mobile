package com.example.buoi4_bai3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buoi4_bai3.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent.getSerializableExtra("dog") as Dog

        binding.ivDogDetail.setImageResource(dog.image)
        binding.tvDogNameDetail.text = dog.name
    }
}
