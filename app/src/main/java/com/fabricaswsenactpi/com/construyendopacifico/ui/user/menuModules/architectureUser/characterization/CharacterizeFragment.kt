package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menuModules.architectureUser.characterization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.databinding.FragmentCharacterizeBinding

class CharacterizeFragment : Fragment(R.layout.fragment_characterize) {

    private lateinit var binding:FragmentCharacterizeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterizeBinding.bind(view)


    }
}