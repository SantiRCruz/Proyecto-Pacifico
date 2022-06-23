package com.fabricaswsenactpi.com.construyendopacifico.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.databinding.ActivityAuthBinding
import com.fabricaswsenactpi.com.construyendopacifico.databinding.ActivityMainBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}