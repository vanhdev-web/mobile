package com.example.buoi4_bai4

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.buoi4_bai4.databinding.ActivityABinding

class ActivityA : AppCompatActivity() {

    private lateinit var binding: ActivityABinding
    private lateinit var folderAdapter: FolderAdapter
    private val folders = mutableListOf<Folder>()

    private val editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == RESULT_OK) {
            val updatedFolder = result.data?.getSerializableExtra("UPDATED_FOLDER") as? Folder
            if (updatedFolder != null) {
                folderAdapter.updateFolder(updatedFolder)
            }
        }
    }

    private val addLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == RESULT_OK) {
            val newFolder = result.data?.getSerializableExtra("NEW_FOLDER") as? Folder
            if (newFolder != null) {
                folderAdapter.addFolder(newFolder)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityABinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupGridView()
        loadInitialData()
    }

    private fun setupToolbar() {
        binding.ivBack.setOnClickListener { finish() }
        binding.tvAdd.setOnClickListener {
            val intent = Intent(this, ActivityC::class.java)
            addLauncher.launch(intent)
        }
    }

    private fun setupGridView() {
        folderAdapter = FolderAdapter(this, folders)
        binding.gridViewFolders.adapter = folderAdapter

        binding.gridViewFolders.setOnItemClickListener { _, _, position, _ ->
            val folder = folders[position]
            val intent = Intent(this, ActivityB::class.java)
            intent.putExtra("FOLDER", folder)
            editLauncher.launch(intent)
        }
    }

    private fun loadInitialData() {
        folders.clear()
        folders.add(Folder(1, "Tổng hợp tin tức thời sự", "Tổng hợp tin tức thời sự nóng hổi nhất, của tất cả các..."))
        folders.add(Folder(2, "Do It Your Self", "Sơn tùng MTP quá đẹp trai hát hay"))
        folders.add(Folder(3, "Cảm hứng sáng tạo", "Tổng hợp tin tức thời sự nóng hổi nhất, của tất cả các..."))
        folderAdapter.notifyDataSetChanged()
    }
}
