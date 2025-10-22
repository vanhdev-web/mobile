package com.example.buoi_3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buoi_3.databinding.ActivityThanhToanBinding
import java.text.NumberFormat
import java.util.Locale

class ThanhToan : AppCompatActivity() {

    private lateinit var binding: ActivityThanhToanBinding
    private var totalFeeVND = 0.0

    private val btcToVndRate = 1_800_000_000.0
    private val ethToVndRate = 60_000_000.0

    companion object {
        const val EXTRA_TOTAL_FEE = "EXTRA_TOTAL_FEE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityThanhToanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWindowInsets()

        totalFeeVND = intent.getDoubleExtra(EXTRA_TOTAL_FEE, 5_000_000.0)

        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
        binding.tvTotalFeeVnd.text = "Tổng học phí: ${formatter.format(totalFeeVND)}"
        
        updatePaymentDetails()

        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            updatePaymentDetails()
        }

        binding.btThanhToan.setOnClickListener {
            val isBitcoin = binding.rbBitcoin.isChecked
            val cryptoSymbol = if (isBitcoin) "BTC" else "ETH"
            val rate = if (isBitcoin) btcToVndRate else ethToVndRate
            val convertedAmount = totalFeeVND / rate
            val vndFormatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))

            val paymentSummary = """
            HÓA ĐƠN THANH TOÁN HỌC PHÍ
            ---------------------------
            Tổng tiền: ${vndFormatter.format(totalFeeVND)}
            Phương thức: Thanh toán bằng $cryptoSymbol
            Số tiền quy đổi: ${"%.8f".format(convertedAmount)} $cryptoSymbol
            
            Cảm ơn bạn đã sử dụng dịch vụ!
            """.trimIndent()

            // Tạo một Implicit Intent để chia sẻ văn bản
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, paymentSummary)
                putExtra(Intent.EXTRA_SUBJECT, "Hóa đơn thanh toán học phí")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Chia sẻ hóa đơn qua")
            startActivity(shareIntent)
        }

        binding.ibExit.setOnClickListener {
            finish()
        }
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun updatePaymentDetails() {
        val isBitcoin = binding.rbBitcoin.isChecked
        val rate = if (isBitcoin) btcToVndRate else ethToVndRate
        val cryptoSymbol = if (isBitcoin) "BTC" else "ETH"
        val imageRes = if (isBitcoin) R.drawable.bitcoin else R.drawable.ethereum

        val convertedAmount = totalFeeVND / rate

        binding.imageView.setImageResource(imageRes)
        binding.tvConvertedFee.text = "Tương đương: ${"%.8f".format(convertedAmount)} $cryptoSymbol"
    }
}
