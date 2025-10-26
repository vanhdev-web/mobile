package com.example.buoi4_bai4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.buoi4_bai4.databinding.ListItemFolderBinding

class FolderAdapter(
    context: Context,
    private val folders: MutableList<Folder>
) : ArrayAdapter<Folder>(context, 0, folders) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding:
        ListItemFolderBinding
        val view:
        View

        if (convertView == null) {
            binding = ListItemFolderBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ListItemFolderBinding
            view = convertView
        }

        val folder = getItem(position)
        if (folder != null) {
            binding.tvFolderName.text = folder.name
            binding.tvFolderDescription.text = folder.description
        }

        return view
    }

    fun addFolder(folder: Folder) {
        add(folder)
    }

    fun updateFolder(updatedFolder: Folder) {
        val position = getPosition(updatedFolder)
        if (position != -1) {
            remove(getItem(position))
            insert(updatedFolder, position)
            notifyDataSetChanged()
        }
    }

    override fun getPosition(item: Folder?): Int {
        if (item == null) return -1
        for (i in 0 until count) {
            if (getItem(i)?.id == item.id) {
                return i
            }
        }
        return -1
    }
}