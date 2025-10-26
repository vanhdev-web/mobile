package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class BookGridView (val activity: Activity,val listBook: List<BookModel>) : ArrayAdapter<BookModel>(activity, R.layout.layout_item, listBook) {
    override fun getCount(): Int {
        return listBook.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = activity.layoutInflater.inflate(R.layout.layout_item, parent, false)

        val imgBook = view.findViewById<ImageView>(R.id.imgBook)
        if (listBook[position].imgBook != 0) {
            imgBook.setImageResource(listBook[position].imgBook)
        } else {
            Glide.with(activity).load(listBook[position].urlBook).into(imgBook)
        }
        val txtBookName = view.findViewById<TextView>(R.id.txtBookName)
        txtBookName.text = listBook[position].txtBookName

        view.setOnClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("bookName", listBook[position].txtBookName)
            if (listBook[position].imgBook != 0) {
                intent.putExtra("bookImage", listBook[position].imgBook)
            } else {
                intent.putExtra("bookUrl", listBook[position].urlBook)
            }
            activity.startActivity(intent)
        }

        return view
        
    }


}