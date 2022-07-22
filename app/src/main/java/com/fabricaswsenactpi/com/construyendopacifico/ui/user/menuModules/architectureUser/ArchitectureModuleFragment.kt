package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menuModules.architectureUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.databinding.FragmentArchitectureModuleBinding

class ArchitectureModuleFragment : Fragment(R.layout.fragment_architecture_module) {
    private lateinit var binding : FragmentArchitectureModuleBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArchitectureModuleBinding.bind(view)


    }

}