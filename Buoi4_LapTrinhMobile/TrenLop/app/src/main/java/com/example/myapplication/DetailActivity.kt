package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imgBookDetail = findViewById<ImageView>(R.id.imgBookDetail)
        val txtBookNameDetail = findViewById<TextView>(R.id.txtBookNameDetail)

        val bookName = intent.getStringExtra("bookName")
        val bookImage = intent.getIntExtra("bookImage", 0)
        val bookUrl = intent.getStringExtra("bookUrl")

        txtBookNameDetail.text = bookName

        if (bookImage != 0) {
            imgBookDetail.setImageResource(bookImage)
        } else {
            Glide.with(this).load(bookUrl).into(imgBookDetail)
        }
    }
}