package com.example.myapplication

import android.os.Bundle
import android.widget.GridView
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

        var listBook = mutableListOf<BookModel>()
        listBook.add(BookModel(0, "https://upload.wikimedia.org/wikipedia/en/thumb/d/d7/Harry_Potter_character_poster.jpg/250px-Harry_Potter_character_poster.jpg", "Harry Potter - hinh internet"))
        listBook.add(BookModel(R.drawable.harrypotter1, "","Harry Potter 1"))
        listBook.add(BookModel(R.drawable.harrypotter2, "","Harry Potter 2"))
        listBook.add(BookModel(R.drawable.harrypotter3, "","Harry Potter 3"))
        listBook.add(BookModel(R.drawable.harrypotter4, "","Harry Potter 4"))
        listBook.add(BookModel(R.drawable.harrypotter5, "","Harry Potter 5"))
        listBook.add(BookModel(R.drawable.harrypotter6, "","Harry Potter 6"))
        listBook.add(BookModel(R.drawable.harrypotter7, "","Harry Potter 7"))
        listBook.add(BookModel(R.drawable.harrypotter8, "","Harry Potter 8"))

        val gridView = findViewById<GridView>(R.id.gvBook)
        gridView.adapter = BookGridView(this, listBook)

    }
}