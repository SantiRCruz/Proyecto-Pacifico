package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menu


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.RetrofitClient
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.department.DepartmentDataSource
import com.fabricaswsenactpi.com.construyendopacifico.databinding.FragmentZonesFormCreateBinding
import com.fabricaswsenactpi.com.construyendopacifico.domain.department.DepartmentRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.core.hide
import com.fabricaswsenactpi.com.construyendopacifico.core.show
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.AppDatabase
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.ethnicgroup.EthnicGroupDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.municipalities.MunicipalitiesDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.populatedcenter.PopulatedCenterDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.population.PopulationDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.*
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population.PopulationBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population.PopulationResponse
import com.fabricaswsenactpi.com.construyendopacifico.domain.ethnicgroup.EthnicGroupRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.domain.municipalities.RepoMunicipalitiesImpl
import com.fabricaswsenactpi.com.construyendopacifico.domain.populatedcenter.PopulatedCenterRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.domain.population.PopulationRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.presentation.*
import com.fabricaswsenactpi.com.construyendopacifico.ui.user.menu.adapter.EthnicGroupAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream


class ZonesFormCreate : Fragment(R.layout.fragment_zones_form_create) {

    private lateinit var binding: FragmentZonesFormCreateBinding
    private var bitmap: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    private val resultRegister =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == Activity.RESULT_OK) {
                    val imageBitmap = it?.data?.extras?.get("data") as Bitmap
                    bitmap = imageBitmap
                    binding.idImageZone.setImageBitmap(bitmap)
                }
            })
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

    private lateinit var departmentList: List<DepartmentResponse>
    private lateinit var municipalitiesList: List<MunicipalitiesResponse>
    private lateinit var populatedCenterList: List<PopulatedCentersResponse>
    private lateinit var ethnicGroupList: List<EthnicGroupResponse>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var selectedEthnicGroup: EthnicGroupResponse
    private lateinit var selectedPopulatedGroup: PopulatedCentersResponse
    private var ethnicInfo = mutableListOf<EthnicGroupResponse>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentZonesFormCreateBinding.bind(view)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        obtainDepartments()
        obtainEthnicGroup()
        buttonsOnClick()
        validate()

    }

    private fun buttonsOnClick() {
        binding.idBtnRegresar.setOnClickListener {
            findNavController().navigate(R.id.action_zonesFormCreate_to_zonesMenu)
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
            obtainMunicipalities(position)
        }
        binding.autocompleteSelectMunicipalities?.setOnItemClickListener { parent, view, position, id ->
            obtainPopulatedCenters(position)
        }
        binding.autocompleteSelectEthnicGroup.setOnItemClickListener { parent, view, position, id ->
            selectedEthnicGroup = ethnicGroupList[position]

            if (ethnicInfo.contains(ethnicGroupList[position])){
                Snackbar.make(binding.root,"Este grupo etnico ya se encuentra agregado",Snackbar.LENGTH_SHORT).show()
            }else{
                ethnicInfo.add(ethnicGroupList[position])
            }
            binding.rvEthnicGroup!!.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            binding.rvEthnicGroup!!.adapter = EthnicGroupAdapter(ethnicInfo)
        }
        binding.imgUpdate2!!.setOnClickListener {
            val data = (binding.rvEthnicGroup!!.adapter as EthnicGroupAdapter ).updateInfo()
            ethnicInfo = data
            binding.rvEthnicGroup!!.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            binding.rvEthnicGroup!!.adapter = EthnicGroupAdapter(ethnicInfo)
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


    private fun saveWebData() {
        val ethnicFinal = mutableListOf<Int>()
        ethnicInfo.forEach {
            ethnicFinal.add(it.id_ethnic_group.toInt())
        }
        Log.e("saveWebData: ",ethnicFinal.toString() )
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val base64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelPopulation.saveWebPopulation(
                    PopulationBody(
                        selectedPopulatedGroup.id_populated_center.toInt(),
                        ethnicFinal,
                        binding.length!!.text.toString(),
                        binding.latitude!!.text.toString(),
                        "0",
                        base64,
                        binding.txtInhabitantsNumber!!.text.toString(),
                    )
                ).collect {
                    when (it) {
                        is Result.Loading -> {
                            binding.btnProgressBar?.show()
                            binding.btnGuardar?.hide()
                        }
                        is Result.Success -> {
                            binding.btnGuardar?.show()
                            binding.btnProgressBar?.hide()
                            if (it.data.results.isNullOrEmpty()){
                                Log.e("saveWebData: ",it.data.toString() )
                                Snackbar.make(
                                    binding.root,
                                    "Esta vereda ya se encuentra registrada ",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }else{
                            Snackbar.make(
                                binding.root,
                                "Se registro correctamente",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            val action =
                                ZonesFormCreateDirections.actionZonesFormCreateToMenuModules22(
                                    it.data.results[0]
                                )
                            findNavController().navigate(action)
                            }
                        }
                        is Result.Failure -> {
                            binding.btnGuardar?.show()
                            binding.btnProgressBar?.hide()
                            Snackbar.make(
                                binding.root,
                                "Error al registrarse",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            Log.e("Error", "sendUser: ${it.exception}")
                        }
                    }
                }
            }
        }
    }

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

    private fun obtainMunicipalities(position: Int) {
        val idDepartment = departmentList.get(position)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelMunicipality.fetchMunicipalities(idDepartment.id_department.toInt())
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

    private fun obtainPopulatedCenters(position: Int) {
        val idMunicipality = municipalitiesList.get(position)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelPopulatedCenter.fetchPopulatedCenters(idMunicipality.id_municipality.toInt())
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

    private fun validate() {
        binding.btnGuardar?.setOnClickListener {
            val results = arrayOf(
                validateDepartment(),
                validateMunicipality(),
                validatePopulatedCenter(),
                validateEthnicGroup(),
                validateInhabitantsNumber(),
            )
            if (false in results) {
                return@setOnClickListener
            }
            saveWebData()
        }
    }

    private fun validateDepartment(): Boolean {
        return if (binding.autocompleteSelectDepartments!!.text.isNullOrEmpty()) {
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

    private fun validateInhabitantsNumber(): Boolean {
        return if (binding.txtInhabitantsNumber?.text.toString().isEmpty()) {
            binding.txtILInhabitantsNumber!!.error = "Este campo es obligatorio"
            false
        } else {
            binding.txtILInhabitantsNumber!!.error = null
            true
        }
    }

}
