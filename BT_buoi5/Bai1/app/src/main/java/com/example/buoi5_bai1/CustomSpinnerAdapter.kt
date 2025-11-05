package com.example.buoi5_bai1

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomSpinnerAdapter(val context: Activity, val list: ArrayList<CustomSpinnerModel>) : BaseAdapter() {

    private class ViewHolder(view: View) {
        val imageViewLogo: ImageView = view.findViewById(R.id.imageViewLogo)
        val tenHangXe: TextView = view.findViewById(R.id.tenHangXe)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = context.layoutInflater.inflate(R.layout.activity_card_view, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val item = list[position]
        holder.imageViewLogo.setImageResource(item.image)
        holder.tenHangXe.text = item.name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent as ViewGroup)
    }
}
