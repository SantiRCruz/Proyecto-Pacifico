package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.core.hide
import com.fabricaswsenactpi.com.construyendopacifico.core.show
import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.RetrofitClient
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.AppDatabase
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.department.DepartmentDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.ethnicgroup.EthnicGroupDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.municipalities.MunicipalitiesDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.populatedcenter.PopulatedCenterDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.population.PopulationDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.PopulationsEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.DepartmentResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.EthnicGroupResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.MunicipalitiesResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.PopulatedCentersResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population.PopulationBody
import com.fabricaswsenactpi.com.construyendopacifico.databinding.FragmentZonesFormUpdateBinding
import com.fabricaswsenactpi.com.construyendopacifico.domain.department.DepartmentRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.domain.ethnicgroup.EthnicGroupRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.domain.municipalities.RepoMunicipalitiesImpl
import com.fabricaswsenactpi.com.construyendopacifico.domain.populatedcenter.PopulatedCenterRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.domain.population.PopulationRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.presentation.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream

class ZonesFormUpdate : Fragment(R.layout.fragment_zones_form_update) {
    private lateinit var binding: FragmentZonesFormUpdateBinding
    private val args by navArgs<ZonesFormUpdateArgs>()
    private var bitmapCreated: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    private val resultRegister =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == Activity.RESULT_OK) {
                    val imageBitmap = it?.data?.extras?.get("data") as Bitmap
                    bitmapCreated = imageBitmap
                    binding.idImageZone.setImageBitmap(bitmapCreated)
                }
            })
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
    private val viewModelDepartment by viewModels<DepartmentViewModel> {
        DepartmentViewModelFactory(
            DepartmentRepoImpl(DepartmentDataSource(RetrofitClient.webService,AppDatabase.getDepartmentsDatabase(requireContext()).DepartmentDao()))
        )
    }
    private val viewModelMunicipality by viewModels<MunicipalitiesViewModel> {
        MunicipalitiesViewModelFactory(
            RepoMunicipalitiesImpl(MunicipalitiesDataSource(RetrofitClient.webService,AppDatabase.getMunicipalitiesDatabase(requireContext()).MunicipalitiesDao()))
        )
    }
    private val viewModelPopulatedCenter by viewModels<PopulatedCenterViewModel> {
        PopulatedCenterViewModelFactory(
            PopulatedCenterRepoImpl(PopulatedCenterDataSource(RetrofitClient.webService,AppDatabase.getPopulatedCentersDatabase(requireContext()).PopulatedCenterDao()))
        )
    }
    private val viewModelEthnicGroup by viewModels<EthnicGroupViewModel> {
        EthnicGroupViewModelFactory(
            EthnicGroupRepoImpl(EthnicGroupDataSource(RetrofitClient.webService))
        )
    }


    private lateinit var departmentList: List<DepartmentResponse>
    private lateinit var municipalitiesList: List<MunicipalitiesResponse>
    private lateinit var populatedCenterList: List<PopulatedCentersResponse>
    private lateinit var ethnicGroupList: List<EthnicGroupResponse>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var selectedEthnicGroup: EthnicGroupResponse
    private lateinit var selectedPopulatedGroup: PopulatedCentersResponse


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentZonesFormUpdateBinding.bind(view)

        selectedPopulatedGroup = PopulatedCentersResponse(args.population.id_populated_center,args.population.municipality_id,args.population.populated_center_name,args.population.populated_center_type,args.population.id_municipality,args.population.department_id,args.population.municipality_name)
//        selectedEthnicGroup = EthnicGroupResponse(args.population.id_ethnic_group.toString(),args.population.ethnic_group_name)
//        reference()
        obtainDepartments()
        obtainMunicipalities(args.population.id_department)
        obtainPopulatedCenters(args.population.id_municipality)
        obtainEthnicGroup()

        buttonsOnClick()
//        validate()


    }

//    private fun reference() {
//        val decodeString = Base64.decode(args.population.photography,Base64.DEFAULT)
//        val bitmap = BitmapFactory.decodeByteArray(decodeString,0,decodeString.size)
//        bitmapCreated = bitmap
//        binding.idImageZone.setImageBitmap(bitmap)
//        binding.autocompleteSelectDepartments.setText( args.population.department_name)
//        binding.autocompleteSelectMunicipalities.setText( args.population.municipality_name)
//        binding.length.text = args.population.length
//        binding.latitude.text = args.population.latitude
//        binding.autocompleteSelectVeredas.setText(args.population.populated_center_name)
//        binding.autocompleteSelectEthnicGroup.setText(args.population.ethnic_group_name)
//        binding.txtSurfaceSources.setText(args.population.surface_sources)
//        binding.txtUndergroundSources.setText(args.population.underground_sources)
//        binding.txtInhabitantsNumber.setText(args.population.inhabitants_number)
//        binding.txtCatchemntType.setText(args.population.catchment_type)
//    }

    private fun buttonsOnClick() {
        binding.idBtnRegresar.setOnClickListener {
            findNavController().navigate(R.id.action_zonesFormUpdate_to_zonesMenu)
        }
        binding.btnUpdateCoordinates!!.setOnClickListener {
            setUpCoordinates()
        }
        binding.btnImgCamera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                resultRegister.launch(takePictureIntent)
            } catch (e: Exception) {
                Snackbar.make(
                    binding.root, "No se encontro ninguna app para abrir la camara",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        binding.autocompleteSelectDepartments?.setOnItemClickListener { parent, view, position, id ->
            binding.autocompleteSelectMunicipalities.setText("")
            binding.autocompleteSelectVeredas.setText("")
            val idDepartment = departmentList[position]
            obtainMunicipalities(idDepartment.id_department.toInt())
        }
        binding.autocompleteSelectMunicipalities?.setOnItemClickListener { parent, view, position, id ->
            binding.autocompleteSelectVeredas.setText("")
            val idMunicipality = municipalitiesList[position]
            obtainPopulatedCenters(idMunicipality.id_municipality)
        }
        binding.autocompleteSelectEthnicGroup?.setOnItemClickListener { parent, view, position, id ->
            selectedEthnicGroup = ethnicGroupList[position]
        }
        binding.autocompleteSelectVeredas?.setOnItemClickListener { parent, view, position, id ->
            selectedPopulatedGroup = populatedCenterList[position]
        }
    }

    private fun setUpCoordinates() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
        } else {
            getLocations()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocations() {
        fusedLocationProviderClient.lastLocation?.addOnSuccessListener {
            if (it == null) {
                Toast.makeText(
                    requireContext(),
                    "no se puede obtener la locacion",
                    Toast.LENGTH_SHORT
                ).show()
            } else it.apply {
                binding.latitude?.text = it.latitude.toString()
                binding.length?.text = it.longitude.toString()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(requireContext(), "aprobado", Toast.LENGTH_SHORT).show()
                    getLocations()
                } else {
                    Toast.makeText(requireContext(), "denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


//    private fun validate() {
//        binding.btnUpdate?.setOnClickListener {
//            val results = arrayOf(
//                validateDepartment(), validateMunicipality(), validatePopulatedCenter(), validateEthnicGroup(), validateSurfaceSources(),
//                validateUndergroundSources(), validateInhabitantsNumber(), validateCatchmentType()
//            )
//            if (false in results) {
//                return@setOnClickListener
//            }
//            saveWebData()
//        }
//    }

//    private fun saveWebData() {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmapCreated?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//        val byteArray = byteArrayOutputStream.toByteArray()
//        val base64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
//        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
//            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModelPopulation.updateWebPopulation(
//                    PopulationBody(
//                        "0",
//                        binding.txtCatchemntType!!.text.toString(),
//                        selectedEthnicGroup.id_ethnic_group.toInt(),
//                        binding.txtInhabitantsNumber!!.text.toString(),
//                        binding.latitude!!.text.toString(),
//                        binding.length!!.text.toString(),
//                        base64,
//                        selectedPopulatedGroup.id_populated_center.toInt(),
//                        binding.txtSurfaceSources!!.text.toString(),
//                        binding.txtUndergroundSources!!.text.toString(),
//                    ),args.population.id_population
//                ).collect {
//                    when (it) {
//                        is Result.Loading -> {
//                            binding.btnProgressBar?.show()
//                            binding.btnUpdate?.hide()
//                        }
//                        is Result.Success -> {
//                            binding.btnUpdate?.show()
//                            binding.btnProgressBar?.hide()
//                            Snackbar.make(
//                                binding.root,
//                                "Se actualizo correctamente",
//                                Snackbar.LENGTH_SHORT
//                            ).show()
//                            findNavController().navigate(R.id.action_zonesFormUpdate_to_zonesMenu)
//                        }
//                        is Result.Failure -> {
//                            binding.btnUpdate?.show()
//                            binding.btnProgressBar?.hide()
//                            Snackbar.make(
//                                binding.root,
//                                "Error al actualizar",
//                                Snackbar.LENGTH_SHORT
//                            ).show()
//                            Log.e("Error", "sendUser: ${it.exception}")
//                        }
//                    }
//                }
//            }
//        }
//    }


//    private fun saveData() {
//        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
//            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModelPopulation.updatePopulation(
//                    PopulationsEntity(
//                        args.population.id_population,
//                        binding.autocompleteSelectVeredas.text.toString(),
//                        binding.autocompleteSelectEthnicGroup.text.toString(),
//                        binding.length!!.text.toString(),
//                        binding.latitude!!.text.toString(),
//                        "",
//                        "",
//                        binding.txtInhabitantsNumber!!.text.toString(),
//                        binding.txtSurfaceSources!!.text.toString(),
//                        binding.txtUndergroundSources!!.text.toString(),
//                        binding.txtCatchemntType!!.text.toString()
//                    )
//                ).collect {
//                    when (it) {
//                        is Result.Loading -> {
//                            binding.progressBar?.show()
//                            binding.btnUpdate?.hide()
//                        }
//                        is Result.Success -> {
//                            binding.btnUpdate?.show()
//                            binding.progressBar?.hide()
//                            Snackbar.make(
//                                binding.root,
//                                "Se Actualizo correctamente",
//                                Snackbar.LENGTH_SHORT
//                            ).show()
//                            findNavController().navigate(R.id.action_zonesFormUpdate_to_zonesMenu)
//                        }
//                        is Result.Failure -> {
//                            binding.btnUpdate?.show()
//                            binding.progressBar?.hide()
//                            Snackbar.make(
//                                binding.root,
//                                "Error al Actualizar",
//                                Snackbar.LENGTH_SHORT
//                            ).show()
//                            Log.e("Error", "sendUser: ${it.exception}")
//                        }
//                    }
//                }
//            }
//        }
//    }

    private fun obtainDepartments() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelDepartment.fetchDepartments().collect {
                    when (it) {
                        is Result.Loading -> {
                            binding.progressBar?.show()
                            binding.screen?.hide()
                        }
                        is Result.Success -> {
                            binding.progressBar?.hide()
                            binding.screen?.show()
                            val departments: MutableList<String> = mutableListOf()
                            departmentList = it.data.results
                            it.data.results.forEach {
                                departments.add(it.department_name)
                            }

                            setUpDepartments(departments)
                        }
                        is Result.Failure -> {
                            binding.progressBar?.hide()
                            binding.screen?.show()
                            Snackbar.make(
                                binding.root,
                                "Error al obtener los datos",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            Log.e("Error", "obtainAbilities: ${it.exception.message}")
                        }
                    }
                }
            }
        }
    }

    private fun obtainMunicipalities(id_department: Int) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelMunicipality.fetchMunicipalities(id_department)
                    .collect {
                        when (it) {
                            is Result.Loading -> {
                                binding.progressBar?.show()
                                binding.screen?.hide()
                            }
                            is Result.Success -> {
                                binding.progressBar?.hide()
                                binding.screen?.show()
                                val municipalities: MutableList<String> = mutableListOf()
                                municipalitiesList = it.data.results
                                it.data.results.forEach {
                                    municipalities.add(it.municipality_name)
                                }

                                setUpMunicipalities(municipalities)
                            }
                            is Result.Failure -> {
                                binding.progressBar?.hide()
                                binding.screen?.show()
                                Snackbar.make(
                                    binding.root,
                                    "Error al obtener los datos",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                                Log.e("Error", "obtainAbilities: ${it.exception.message}")
                            }
                        }
                    }
            }
        }
    }

    private fun obtainPopulatedCenters(id_municipality: Int) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelPopulatedCenter.fetchPopulatedCenters(id_municipality)
                    .collect {
                        when (it) {
                            is Result.Loading -> {
                                binding.progressBar?.show()
                                binding.screen?.hide()
                            }
                            is Result.Success -> {
                                binding.progressBar?.hide()
                                binding.screen?.show()
                                val populatedCenters: MutableList<String> = mutableListOf()
                                populatedCenterList = it.data.results
                                it.data.results.forEach {
                                    populatedCenters.add(it.populated_center_name)
                                }

                                setUpPopulatedCenters(populatedCenters)
                            }
                            is Result.Failure -> {
                                binding.progressBar?.hide()
                                binding.screen?.show()
                                Snackbar.make(
                                    binding.root,
                                    "Error al obtener los datos",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                                Log.e("Error", "obtainAbilities: ${it.exception.message}")
                            }
                        }
                    }
            }
        }
    }

    private fun obtainEthnicGroup() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelEthnicGroup.fetchDepartments().collect {
                    when (it) {
                        is Result.Loading -> {
                            binding.progressBar?.show()
                            binding.screen?.hide()
                        }
                        is Result.Success -> {
                            binding.progressBar?.hide()
                            binding.screen?.show()
                            val ethnicGroups: MutableList<String> = mutableListOf()
                            ethnicGroupList = it.data.results
                            it.data.results.forEach {
                                ethnicGroups.add(it.ethnic_group_name)
                            }

                            setUpEthnicGroup(ethnicGroups)
                        }
                        is Result.Failure -> {
                            binding.progressBar?.hide()
                            binding.screen?.show()
                            Snackbar.make(
                                binding.root,
                                "Error al obtener los datos",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            Log.e("Error", "obtainAbilities: ${it.exception.message}")
                        }
                    }
                }
            }
        }
    }

    private fun validateDepartment(): Boolean {
        return if (binding.autocompleteSelectDepartments?.text.toString().isNullOrEmpty()) {
            binding.autocompleteSelectDepartments!!.error = "Este campo es obligatorio"
            false
        } else {
            binding.autocompleteSelectDepartments!!.error = null
            true
        }
    }

    private fun validateMunicipality(): Boolean {
        return if (binding.autocompleteSelectMunicipalities.text.toString().isNullOrEmpty()) {
            binding.autocompleteSelectMunicipalities.error = "Este campo es obligatorio"
            false
        } else {
            binding.autocompleteSelectMunicipalities.error = null
            true
        }
    }

    private fun validatePopulatedCenter(): Boolean {
        return if (binding.autocompleteSelectVeredas.text.toString().isNullOrEmpty()) {
            binding.autocompleteSelectVeredas.error = "Este campo es obligatorio"
            false
        } else {
            binding.autocompleteSelectVeredas.error = null
            true
        }
    }

    private fun validateEthnicGroup(): Boolean {
        return if (binding.autocompleteSelectEthnicGroup.text.toString().isEmpty()) {
            binding.autocompleteSelectEthnicGroup.error = "Este campo es obligatorio"
            false
        } else {
            binding.autocompleteSelectEthnicGroup.error = null
            true
        }
    }

//    private fun validateSurfaceSources(): Boolean {
//        return if (binding.txtSurfaceSources?.text.toString().isEmpty()) {
//            binding.txtILSurfaceSources!!.error = "Este campo es obligatorio"
//            false
//        } else {
//            binding.txtILSurfaceSources!!.error = null
//            true
//        }
//    }

//    private fun validateUndergroundSources(): Boolean {
//        return if (binding.txtUndergroundSources?.text.toString().isEmpty()) {
//            binding.txtILUnderGroundSources!!.error = "Este campo es obligatorio"
//            false
//        } else {
//            binding.txtILUnderGroundSources!!.error = null
//            true
//        }
//    }

    private fun validateInhabitantsNumber(): Boolean {
        return if (binding.txtInhabitantsNumber?.text.toString().isEmpty()) {
            binding.txtILInhabitantsNumber!!.error = "Este campo es obligatorio"
            false
        } else {
            binding.txtILInhabitantsNumber!!.error = null
            true
        }
    }

//    private fun validateCatchmentType(): Boolean {
//        return if (binding.txtCatchemntType?.text.toString().isEmpty()) {
//            binding.txtILCatchmentType!!.error = "Este campo es obligatorio"
//            false
//        } else {
//            binding.txtILCatchmentType!!.error = null
//            true
//        }
//    }

    private fun setUpDepartments(departments: MutableList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, departments)
        binding.autocompleteSelectDepartments?.setAdapter(adapter)
    }

    private fun setUpMunicipalities(municipalities: MutableList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, municipalities)
        binding.autocompleteSelectMunicipalities?.setAdapter(adapter)
    }

    private fun setUpPopulatedCenters(populatedCenters: MutableList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, populatedCenters)
        binding.autocompleteSelectVeredas?.setAdapter(adapter)
    }

    private fun setUpEthnicGroup(ethnic: MutableList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, ethnic)
        binding.autocompleteSelectEthnicGroup.setAdapter(adapter)
    }

}