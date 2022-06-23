package com.fabricaswsenactpi.com.construyendopacifico

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.fabricaswsenactpi.com.construyendopacifico.databinding.ActivityMainBinding
import com.fabricaswsenactpi.com.construyendopacifico.databinding.FragmentZonesMenuBinding
import com.fabricaswsenactpi.com.construyendopacifico.ui.user.menu.ZonesMenu
import androidx.navigation.fragment.findNavController

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fabricaswsenactpi.com.construyendopacifico.core.AppConstants
import com.fabricaswsenactpi.com.construyendopacifico.ui.auth.AuthActivity


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.exit.setOnClickListener {
            val intentMain =
                Intent(this, AuthActivity::class.java)
            startActivity(intentMain)
            finish()
        }

        val shared = getSharedPreferences(AppConstants.SHARED_USER,Context.MODE_PRIVATE)
        val names = shared.getString("userNames","")
        val lastNames = shared.getString("userLastNames","")
        binding.userName.text = "$names $lastNames"

    }

}