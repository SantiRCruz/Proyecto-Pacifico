package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menu

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.core.hide
import com.fabricaswsenactpi.com.construyendopacifico.core.show
import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.RetrofitClient
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.AppDatabase
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.population.PopulationDataSource
import com.fabricaswsenactpi.com.construyendopacifico.databinding.ConfirmDeleteZoneBinding
import com.fabricaswsenactpi.com.construyendopacifico.databinding.FragmentZonesMenuBinding
import com.fabricaswsenactpi.com.construyendopacifico.domain.population.PopulationRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.presentation.PopulationViewModel
import com.fabricaswsenactpi.com.construyendopacifico.presentation.PopulationViewModelFactory
import com.fabricaswsenactpi.com.construyendopacifico.ui.user.menu.adapter.ZonesMenuAdapter
import com.google.android.material.snackbar.Snackbar

class ZonesMenu : Fragment(R.layout.fragment_zones_menu) {
    private lateinit var binding: FragmentZonesMenuBinding
    private val viewModelPopulation by viewModels<PopulationViewModel> {
        PopulationViewModelFactory(
            PopulationRepoImpl(
                PopulationDataSource(
                    AppDatabase.getPopulationsDatabase(
                        requireContext()
                    ).PopulationDao(),
                    RetrofitClient.webService
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentZonesMenuBinding.bind(view)

        setUpPopulations()

        binding.idBtnRegisterNewZone.setOnClickListener {
            findNavController().navigate(R.id.action_zonesMenu2_to_zonesFormCreate)
        }
    }

    private fun setUpPopulations(){
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModelPopulation.fetchWebPopulations().collect {
                    when(it){
                        is Result.Loading -> {
                            binding.progressBar.show()
                            binding.rvPopulation.hide()
                        }
                        is Result.Success -> {
                            binding.progressBar.hide()
                            if(it.data.results.isNullOrEmpty()){
                                binding.noData.show()
                                binding.rvPopulation.hide()
                            }else{
                                binding.noData.hide()
                                binding.rvPopulation.show()
                            }
                            val adapter = ZonesMenuAdapter(it.data.results,viewModelPopulation,requireContext())
                            binding.rvPopulation.layoutManager = GridLayoutManager(requireContext(),3)
                            binding.rvPopulation.adapter = adapter
                        }
                        is Result.Failure -> {
                            binding.progressBar.hide()
                            Snackbar.make(binding.root,"Error del servidor ${it.exception}",
                                Snackbar.LENGTH_SHORT).show()
                            Log.e("Errorrrrrrrrrrrrrrr","${it.exception}")
                        }
                    }
                }
            }
        }
    }

}