package com.example.buoi4_bai4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buoi4_bai4.databinding.ActivityCBinding

class ActivityC : AppCompatActivity() {

    private lateinit var binding: ActivityCBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
    }

    private fun setupToolbar() {
        binding.tvCancel.setOnClickListener { finish() }
        binding.tvSave.setOnClickListener { saveNewFolder() }
    }

    private fun saveNewFolder() {
        val folderName = binding.etFolderName.text.toString()
        val folderDescription = binding.etFolderDescription.text.toString()

        if (folderName.isNotEmpty()) {
            val newFolder = Folder(
                id = System.currentTimeMillis(), // Unique ID
                name = folderName,
                description = folderDescription
            )

            val resultIntent = Intent()
            resultIntent.putExtra("NEW_FOLDER", newFolder)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
