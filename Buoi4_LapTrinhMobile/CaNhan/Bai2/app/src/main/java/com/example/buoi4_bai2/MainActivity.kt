package com.example.buoi4_bai2

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.buoi4_bai2.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTextWatchers()
        setupDatePicker()
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                validateFields()
            }
        }

        binding.etFullName.addTextChangedListener(textWatcher)
        binding.etDob.addTextChangedListener(textWatcher)
        binding.etPhone.addTextChangedListener(textWatcher)
        binding.etEmail.addTextChangedListener(textWatcher)
        binding.etIdCard.addTextChangedListener(textWatcher)
    }

    private fun setupDatePicker() {
        binding.etDob.setOnClickListener {
            showDatePickerDialog()
        }
        binding.ivCalendar.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, {
            _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            binding.etDob.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun validateFields() {
        val fullName = binding.etFullName.text.toString().trim()
        val dob = binding.etDob.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val idCard = binding.etIdCard.text.toString().trim()

        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (email.isNotEmpty() && !isEmailValid) {
            binding.etEmail.error = "Email không hợp lệ"
        } else {
            binding.etEmail.error = null
        }

        val isPhoneValid = phone.length == 10
        if (phone.isNotEmpty() && !isPhoneValid) {
            binding.etPhone.error = "Số điện thoại không hợp lệ"
        } else {
            binding.etPhone.error = null
        }

        val isIdCardValid = idCard.length == 12
        if (idCard.isNotEmpty() && !isIdCardValid) {
            binding.etIdCard.error = "Số CMT không hợp lệ"
        } else {
            binding.etIdCard.error = null
        }

        binding.btnUpdate.isEnabled = fullName.isNotEmpty() &&
                dob.isNotEmpty() &&
                isPhoneValid &&
                isEmailValid &&
                isIdCardValid
    }
}