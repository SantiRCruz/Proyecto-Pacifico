package com.fabricaswsenactpi.com.construyendopacifico.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fabricaswsenactpi.com.construyendopacifico.MainActivity
import com.fabricaswsenactpi.com.construyendopacifico.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnLogin.setOnClickListener {
            jumpToActivityMain()
        }
    }

    private fun jumpToActivityMain() {

        val intentMain = Intent(this, MainActivity::class.java)
        startActivity(intentMain)
        finish()
    }
}