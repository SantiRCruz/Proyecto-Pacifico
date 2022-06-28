package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menuModules.constructionUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.databinding.FragmentConstructionBinding

class ConstructionFragment3 : Fragment(R.layout.fragment_construction3) {
    private lateinit var binding: FragmentConstructionBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConstructionBinding.bind(view)

      // clicks()

    }
/*
    private fun clicks() {
        private fun clicks() {
            binding.btnNext.setOnClickListener{
                findNavController().navigate(R.id.constructionFragment4)
            }
    }

 */

}