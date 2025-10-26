package com.example.buoi4_bai3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView

class DogAdapter(context: Context, private val dogs: List<Dog>) : ArrayAdapter<Dog>(context, 0, dogs) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false)

        val dog = dogs[position]

        val ivDog = view.findViewById<ImageView>(R.id.ivDog)
        ivDog.setImageResource(dog.image)

        return view
    }
}
