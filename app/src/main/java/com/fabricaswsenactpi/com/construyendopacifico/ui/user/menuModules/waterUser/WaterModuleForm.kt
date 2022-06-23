package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menuModules.waterUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.databinding.FragmentWaterModuleFormBinding

class WaterModuleForm : Fragment(R.layout.fragment_water_module_form) {
    private lateinit var binding: FragmentWaterModuleFormBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWaterModuleFormBinding.bind(view)

        binding.IdBtnSave.setOnClickListener {
        }
    }
}