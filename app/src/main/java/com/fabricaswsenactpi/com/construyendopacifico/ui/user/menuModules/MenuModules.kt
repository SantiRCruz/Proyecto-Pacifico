package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menuModules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.databinding.FragmentMenuModulesBinding
import com.fabricaswsenactpi.com.construyendopacifico.ui.user.menu.ZonesFormUpdateArgs

class MenuModules : Fragment(R.layout.fragment_menu_modules) {
    private lateinit var binding:FragmentMenuModulesBinding
    private val args by navArgs<MenuModulesArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentMenuModulesBinding.bind(view)

        binding.idCardWater.setOnClickListener {
            val action = MenuModulesDirections.actionMenuModules2ToAnalyzeWaterParameters(args.population)
            findNavController().navigate(action)
        }
        binding.btnBackToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_menuModules2_to_zonesMenu)
        }
        binding.txtArquitecture.setOnClickListener{
            findNavController().navigate(R.id.constructionFragment)
        }
    }

}