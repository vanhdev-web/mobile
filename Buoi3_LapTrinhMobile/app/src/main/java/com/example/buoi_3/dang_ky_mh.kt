package com.example.buoi_3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buoi_3.databinding.ActivityDangKyMhBinding

class dang_ky_mh : AppCompatActivity() {

    private lateinit var binding: ActivityDangKyMhBinding

    private val courseFees = mapOf(
        "Hệ thống thông tin" to 1_500_000.0,
        "Tin học đại cương" to 1_200_000.0,
        "Cơ sở dữ liệu" to 1_800_000.0,
        "Lập trình di động" to 2_500_000.0,
        "Anh văn" to 1_000_000.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDangKyMhBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWindowInsets()

        val username = intent.getStringExtra("username")
        binding.textView.text = "Xin chào $username, bạn hãy đăng ký môn học:"

        binding.btDangKy.setOnClickListener {
            displaySelectedCourses()
        }

        binding.btXacNhan.setOnClickListener {
            when {
                binding.rbTamThoi.isChecked -> {
                    Toast.makeText(this, "Đã lưu đăng ký tạm thời", Toast.LENGTH_SHORT).show()
                }
                binding.rbChinhThuc.isChecked -> {
                    val totalFee = calculateTotalFee()
                    if (totalFee > 0) {
                        val intent = Intent(this, ThanhToan::class.java).apply {
                            putExtra(ThanhToan.EXTRA_TOTAL_FEE, totalFee)
                        }
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Vui lòng đăng ký ít nhất một môn học", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {
                    Toast.makeText(this, "Vui lòng chọn tình trạng đăng ký", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun displaySelectedCourses() {
        var selectedCourses = ""
        if (binding.heThongThongTin.isChecked) {
            selectedCourses += "- ${binding.heThongThongTin.text}\n"
        }
        if (binding.tinHocDaiCuong.isChecked) {
            selectedCourses += "- ${binding.tinHocDaiCuong.text}\n"
        }
        if (binding.coSoDuLieu.isChecked) {
            selectedCourses += "- ${binding.coSoDuLieu.text}\n"
        }
        if (binding.lapTrinhDiDong.isChecked) {
            selectedCourses += "- ${binding.lapTrinhDiDong.text}\n"
        }
        if (binding.anhVan.isChecked) {
            selectedCourses += "- ${binding.anhVan.text}\n"
        }
        binding.textView2.text = "Danh sách môn đăng ký:"
        if (selectedCourses.isNotEmpty()) {
            binding.textView2.text = "Các môn đã đăng ký:\n${selectedCourses.trim()}"
        } else {
            binding.textView2.text = "Bạn chưa đăng ký môn học nào."
        }
    }

    private fun calculateTotalFee(): Double {
        var total = 0.0
        if (binding.heThongThongTin.isChecked) total += courseFees[binding.heThongThongTin.text.toString()] ?: 0.0
        if (binding.tinHocDaiCuong.isChecked) total += courseFees[binding.tinHocDaiCuong.text.toString()] ?: 0.0
        if (binding.coSoDuLieu.isChecked) total += courseFees[binding.coSoDuLieu.text.toString()] ?: 0.0
        if (binding.lapTrinhDiDong.isChecked) total += courseFees[binding.lapTrinhDiDong.text.toString()] ?: 0.0
        if (binding.anhVan.isChecked) total += courseFees[binding.anhVan.text.toString()] ?: 0.0
        return total
    }
}
