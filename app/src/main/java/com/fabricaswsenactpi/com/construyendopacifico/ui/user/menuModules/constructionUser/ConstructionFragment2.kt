package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menuModules.constructionUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.databinding.FragmentConstruction2Binding

class ConstructionFragment2 : Fragment(R.layout.fragment_construction2) {
    private lateinit var binding: FragmentConstruction2Binding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConstruction2Binding.bind(view)
        clicks();

    }

    private fun clicks() {

    }

}

