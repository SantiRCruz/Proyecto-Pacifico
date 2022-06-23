package com.fabricaswsenactpi.com.construyendopacifico.ui.auth

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.RetrofitClient
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.AppDatabase
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.department.DepartmentDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.municipalities.MunicipalitiesDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.populatedcenter.PopulatedCenterDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.DepartmentEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.MunicipalitiesEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.PopulatedCentersEntity
import com.fabricaswsenactpi.com.construyendopacifico.databinding.FragmentSplashBinding
import com.fabricaswsenactpi.com.construyendopacifico.domain.department.DepartmentRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.domain.municipalities.RepoMunicipalitiesImpl
import com.fabricaswsenactpi.com.construyendopacifico.domain.populatedcenter.PopulatedCenterRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.presentation.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private lateinit var binding: FragmentSplashBinding
    private val viewModel by viewModels<DepartmentViewModel> {
        DepartmentViewModelFactory(
            DepartmentRepoImpl(
                DepartmentDataSource(
                    RetrofitClient.webService,
                    AppDatabase.getDepartmentsDatabase(requireContext()).DepartmentDao()
                )
            )
        )
    }
    private val viewModelMunicipality by viewModels<MunicipalitiesViewModel> {
        MunicipalitiesViewModelFactory(
            RepoMunicipalitiesImpl(
                MunicipalitiesDataSource(
                    RetrofitClient.webService,
                    AppDatabase.getMunicipalitiesDatabase(requireContext()).MunicipalitiesDao()
                )
            )
        )
    }
    private val viewModelPopulatedCenter by viewModels<PopulatedCenterViewModel> {
        PopulatedCenterViewModelFactory(
            PopulatedCenterRepoImpl(
                PopulatedCenterDataSource(
                    RetrofitClient.webService,
                    AppDatabase.getPopulatedCentersDatabase(requireContext()).PopulatedCenterDao()
                )
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)

        obtainAllDepartments()
        obtainAllMunicipalities()
        obtainAllPopulatedCenters()

        startTimer()

    }

    private fun obtainAllPopulatedCenters() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelPopulatedCenter.fetchAllPopulatedCenters().collect {
                    when (it) {
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                            it.data.results.forEach { response ->
                                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                                    viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                        viewModelPopulatedCenter.saveDBPopulatedCenter(
                                            PopulatedCentersEntity(
                                                response.id_populated_center,
                                                response.municipality_id,
                                                response.populated_center_name,
                                                response.populated_center_type,
                                            )
                                        ).collect { save ->
                                            when (save) {
                                                is Result.Loading -> {}
                                                is Result.Success -> {}
                                                is Result.Failure -> {
                                                    Log.e(
                                                        "obtainAllDepartments: ",
                                                        save.exception.message.toString()
                                                    )
                                                    Snackbar.make(
                                                        binding.root,
                                                        "Error!",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        is Result.Failure -> {
                            Log.e("obtainAllDepartments: ", it.exception.message.toString())
                            Snackbar.make(binding.root, "Error!", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }


    private fun obtainAllMunicipalities() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelMunicipality.fetchAllMunicipalities().collect {
                    when (it) {
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                            it.data.results.forEach { response ->
                                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                                    viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                        viewModelMunicipality.saveDBMunicipalities(
                                            MunicipalitiesEntity(
                                                response.id_municipality,
                                                response.department_id,
                                                response.municipality_name
                                            )
                                        ).collect { save ->
                                            when (save) {
                                                is Result.Loading -> {}
                                                is Result.Success -> {}
                                                is Result.Failure -> {
                                                    Log.e(
                                                        "obtainAllDepartments: ",
                                                        save.exception.message.toString()
                                                    )
                                                    Snackbar.make(
                                                        binding.root,
                                                        "Error!",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        is Result.Failure -> {
                            Log.e("obtainAllDepartments: ", it.exception.message.toString())
                            Snackbar.make(binding.root, "Error!", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun obtainAllDepartments() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchDepartments().collect {
                    when (it) {
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                            it.data.results.forEach { response ->
                                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                                    viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                        viewModel.saveDBDepartment(
                                            DepartmentEntity(
                                                response.id_department.toInt(),
                                                response.department_name
                                            )
                                        ).collect { save ->
                                            when (save) {
                                                is Result.Loading -> {}
                                                is Result.Success -> {}
                                                is Result.Failure -> {
                                                    Log.e(
                                                        "obtainAllDepartments: ",
                                                        save.exception.message.toString()
                                                    )
                                                    Snackbar.make(
                                                        binding.root,
                                                        "Error!",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        is Result.Failure -> {
                            Log.e("obtainAllDepartments: ", it.exception.message.toString())
                            Snackbar.make(binding.root, "Error!", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun startTimer() {
        object : CountDownTimer(2000, 100) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }.start()
    }
}