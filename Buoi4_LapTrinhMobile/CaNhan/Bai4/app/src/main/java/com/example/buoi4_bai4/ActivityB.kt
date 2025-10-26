package com.example.buoi4_bai4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buoi4_bai4.databinding.ActivityBBinding

class ActivityB : AppCompatActivity() {

    private lateinit var binding: ActivityBBinding
    private var folder: Folder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBBinding.inflate(layoutInflater)
        setContentView(binding.root)

        folder = intent.getSerializableExtra("FOLDER") as? Folder

        folder?.let {
            binding.etFolderName.setText(it.name)
            binding.etFolderDescription.setText(it.description)
        }

        setupToolbar()
    }

    private fun setupToolbar() {
        binding.tvCancel.setOnClickListener { finish() }
        binding.tvSave.setOnClickListener { saveChanges() }
    }

    private fun saveChanges() {
        folder?.apply {
            name = binding.etFolderName.text.toString()
            description = binding.etFolderDescription.text.toString()
        }

        val resultIntent = Intent()
        resultIntent.putExtra("UPDATED_FOLDER", folder)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
