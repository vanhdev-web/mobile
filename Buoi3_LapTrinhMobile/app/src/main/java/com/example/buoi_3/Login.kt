package com.example.buoi_3

import android.os.Bundle
import android.widget.Toast
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buoi_3.databinding.ActivityLoginBinding
import android.content.Intent




class Login : AppCompatActivity(){

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        binding.btLogin.setOnClickListener(this)
//        {
//            Toast.makeText(this, binding.editTextUsername.text.toString(), Toast.LENGTH_SHORT).show()
//        }
        addEvent()

    }
    private fun addEvent(){
        binding.btLogin.setOnClickListener {
            buttonLogin()
        }


    }

    private fun buttonLogin() {
        Toast.makeText(this, binding.editTextUsername.text.toString(), Toast.LENGTH_SHORT).show()
        var i = Intent(this, dang_ky_mh::class.java)
        i.putExtra("username", binding.editTextUsername.text.toString())
        startActivity(i)

    }

}