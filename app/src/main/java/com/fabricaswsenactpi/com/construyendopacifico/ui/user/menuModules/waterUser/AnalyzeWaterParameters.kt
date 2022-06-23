package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menuModules.waterUser

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.core.AppConstants
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.core.hide
import com.fabricaswsenactpi.com.construyendopacifico.core.show
import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.RetrofitClient
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.AppDatabase
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.analysis.AnalysisDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.measure.MeasureDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.parameter.ParameterDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.population.PopulationDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.sample.SampleDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.MeasureEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.PopulationsEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.SamplesEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.EthnicGroupResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.ParameterResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.analysis.AnalysisBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.analysis.AnalysisResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleResponse
import com.fabricaswsenactpi.com.construyendopacifico.databinding.AddSamplesBinding
import com.fabricaswsenactpi.com.construyendopacifico.databinding.ConfirmDeleteZoneBinding
import com.fabricaswsenactpi.com.construyendopacifico.databinding.FragmentAnalyzeWaterParametersBinding
import com.fabricaswsenactpi.com.construyendopacifico.domain.analysis.AnalysisRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.domain.measure.MeasureRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.domain.parameter.ParameterRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.domain.population.PopulationRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.domain.sample.SampleRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.presentation.*
import com.fabricaswsenactpi.com.construyendopacifico.ui.user.menu.ZonesFormCreateDirections
import com.fabricaswsenactpi.com.construyendopacifico.ui.user.menu.adapter.ZonesMenuAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

class AnalyzeWaterParameters : Fragment(R.layout.fragment_analyze_water_parameters),RadioGroup.OnCheckedChangeListener {
    private lateinit var binding: FragmentAnalyzeWaterParametersBinding
    private val args by navArgs<AnalyzeWaterParametersArgs>()
    private lateinit var dialogBinding: AddSamplesBinding
    private val viewModelMeasure by viewModels<MeasureViewModel> {
        MeasureViewModelFactory(
            MeasureRepoImpl(
                MeasureDataSource(
                    AppDatabase.getMeasureDatabase(
                        requireContext()
                    ).MeasureDao()
                )
            )
        )
    }
    private val viewModelSample by viewModels<SampleViewModel> {
        SampleViewModelFactory(
            SampleRepoImpl(
                SampleDataSource(
                    AppDatabase.getSamplesDatabase(
                        requireContext()
                    ).SampleDao(),
                    RetrofitClient.webService
                )
            )
        )
    }
    private val viewModelParameter by viewModels<ParameterViewModel> {
        ParameterViewModelFactory(
            ParameterRepoImpl(
                ParameterDataSource(
                    RetrofitClient.webService
                )
            )
        )
    }
    private val viewModelAnalysis by viewModels<AnalysisViewModel> {
        AnalysisViewModelFactory(
            AnalysisRepoImpl(
                AnalysisDataSource(
                    RetrofitClient.webService
                )
            )
        )
    }

    private lateinit var dateFormat:String
    private lateinit var hourFormat:String
    private var waterType:Int = 0
    private lateinit var parametersList: List<ParameterResponse>
    private lateinit var analysisList: List<AnalysisResponse>
    private lateinit var selectedParameter: ParameterResponse
    private val AccepatbleSamplesList = listOf("Aceptable","No Aceptable")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnalyzeWaterParametersBinding.bind(view)

        reference()
        obtainAnalysis()
        buttonClicks()
        obtainParameters()
        validateSaveAnalysis()
        validate()

    }

    private fun obtainSamples() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModelSample.fetchSamplesByAnalysis(analysisList[0].id_analysis).collect {
                    when(it){
                        is Result.Loading->{
                            binding.progressBar?.show()
                            binding.screen?.hide()
                        }
                        is Result.Success->{
                            binding.progressBar?.hide()
                            binding.screen?.show()
                            if(it.data.results.isNullOrEmpty()){
                                binding.noSamples.show()
                                binding.rvSamples.hide()
                            }else{
                                binding.noSamples.hide()
                                binding.rvSamples.show()
                            }
                            val adapter = WaterSamplesAdapter(it.data.results,requireContext())
                            binding.rvSamples.layoutManager = LinearLayoutManager(requireContext())
                            binding.rvSamples.adapter = adapter

                        }
                        is Result.Failure->{
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

    private fun obtainAnalysis() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModelAnalysis.fetchAnalysisByPopulation(args.population.id_population).collect {
                    when(it){
                        is Result.Loading ->{
                            binding.progressBar?.show()
                            binding.screen?.hide()
                        }
                        is Result.Success ->{
                            binding.progressBar?.hide()
                            binding.screen?.show()

                            if (!it.data.results.isNullOrEmpty()){
                                binding.btnSaveAnalysis.hide()
                                binding.screenNoAnalysis.hide()
                                binding.screenSamples.show()
                                enableCheckButtons(false)
                                selectedCheckButton(it.data.results[0].water_type_id)
                                binding.txtVDate.text = "${it.data.results[0].date} ${it.data.results[0].hour}"
                                analysisList = it.data.results
                                obtainSamples()
                            }
                        }
                        is Result.Failure ->{
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

    private fun enableCheckButtons(param:Boolean) {
        binding.cbNaturalWater.isEnabled=param
        binding.cbTreatedWater.isEnabled=param
        binding.cbResidualWater.isEnabled=param
        binding.cbOtherWater.isEnabled=param
    }

    private fun selectedCheckButton(waterId:Int){
        when(waterId) {
            1 -> { binding.cbNaturalWater.isChecked = true }
            2 -> { binding.cbTreatedWater.isChecked = true }
            3 -> { binding.cbResidualWater.isChecked = true }
            4 -> { binding.cbOtherWater.isChecked = true }
        }
    }

    private fun obtainParameters() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelParameter.fetchParameter().collect {
                    when (it) {
                        is Result.Loading -> {
                            binding.progressBar?.show()
                            binding.screen?.hide()
                        }
                        is Result.Success -> {
                            binding.progressBar?.hide()
                            binding.screen?.show()
                            val parameters: MutableList<String> = mutableListOf()
                            parametersList = it.data.results
                            it.data.results.forEach {
                                parameters.add(it.parameter_name)
                            }
                            setUpParameters(parameters)
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

    private fun buttonClicks() {
        binding.imgBtnBack.setOnClickListener {
            findNavController().navigate(R.id.action_analyzeWaterParameters_to_zonesMenu)
        }
        binding.radioGroup.setOnCheckedChangeListener(this)
        binding.txtFParameter.setOnItemClickListener { parent, view, position, id ->
            selectedParameter = parametersList[position]
        }

    }

    private fun reference() {
        val date = Date(System.currentTimeMillis())
        dateFormat = SimpleDateFormat("yyyy.MM.dd").format(date)

        hourFormat = SimpleDateFormat("hh:mm aaa").format(date)

        binding.txtVMunicipality.text = args.population.municipality_name
        binding.txtVPopulatedCenter. text = args.population.populated_center_name
        binding.txtVDate.text = "$dateFormat $hourFormat"
    }

    private fun validate() {
        binding.btnAddSample?.setOnClickListener {
            val results = arrayOf(
                validateParameter(),
            )
            if (false in results) {
                return@setOnClickListener
            }
            addSample()
        }
    }

    private fun validateSaveAnalysis(){
        binding.btnSaveAnalysis?.setOnClickListener {
            if (waterType == 0) {
                binding.cbNaturalWater.error = "Debe estar minimo un campo seleccionado"
                return@setOnClickListener
            }
            saveAnalysis()
        }
    }

    private fun saveAnalysis() {
        val shared = requireActivity().getSharedPreferences(AppConstants.SHARED_USER,Context.MODE_PRIVATE)
        val idUser = shared.getInt("idUser",0)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModelAnalysis.saveAnalysis(AnalysisBody(
                    dateFormat,
                    hourFormat,
                    args.population.id_population,
                    "prueba",
                    1,
                    idUser,
                    waterType
                )).collect {
                    when(it){
                        is Result.Loading ->{
                            enableCheckButtons(false)
                            binding.btnSaveAnalysis.hide()
                            binding.btnSaveProgressBar.show()
                        }
                        is Result.Success ->{
                            binding.screenNoAnalysis.hide()
                            binding.screenSamples.show()
                            binding.btnSaveProgressBar.hide()
                            analysisList = it.data.results

                        }
                        is Result.Failure ->{
                            enableCheckButtons(true)
                            binding.btnSaveProgressBar.hide()
                            Snackbar.make(
                                binding.root,
                                "Error al guardar el analisis",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            Log.e("Error", "save Analisis: ${it.exception.message}")
                        }
                    }
                }
            }
        }
    }

    private fun validateParameter(): Boolean {
        return if (binding.txtFParameter?.text.toString().isNullOrEmpty()) {
            binding.txtFParameter!!.error = "Este campo no puede estar vacio"
            false
        } else {
            binding.txtFParameter!!.error = null
            true
        }
    }

    private fun setUpParameters(parameters:MutableList<String>) {
        val adapter = ArrayAdapter(requireContext(),R.layout.list_item,parameters)
        binding.txtFParameter.setAdapter(adapter)
    }


    private fun addSample() {

        val dialogBinding = AddSamplesBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
        }.create()
//        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        dialogBinding.txtTitleParameter.text = "Ingrese el dato de la muestra de ${binding.txtFParameter.text.toString()} :"
        if (selectedParameter.id_parameter == 2){

            val adapter = ArrayAdapter(requireContext(),R.layout.list_item,AccepatbleSamplesList)
            dialogBinding.autocompleteSelectSamplesAcceptable.setAdapter(adapter)

            dialogBinding.txtILSampleAcceptable.show()
            dialogBinding.txtILSample.hide()
        }
        dialogBinding.imgClose.setOnClickListener { dialog.dismiss() }
        dialogBinding.btnSample.setOnClickListener {
            var valueSample = ""
            if (!dialogBinding.edtSample.text.toString().isNullOrEmpty()){
                valueSample = dialogBinding.edtSample.text.toString()
            }else if(!dialogBinding.autocompleteSelectSamplesAcceptable.text.toString().isNullOrEmpty()){
                valueSample = dialogBinding.autocompleteSelectSamplesAcceptable.text.toString()
            }
            val results = if (valueSample.isNullOrEmpty()) {
                dialogBinding.txtILSample.error = "Este campo no puede estar vacio"
                dialogBinding.txtILSampleAcceptable.error = "Este campo no puede estar vacio"
                false
            } else {
                dialogBinding.txtILSample.error = null
                dialogBinding.txtILSampleAcceptable.error = null
                true
            }
            if (!results) {
                return@setOnClickListener
            }
            saveSample(valueSample,dialogBinding,dialog)
             }
        dialog.setCancelable(false)
        dialog.show()


    }

    private fun saveSample(value:String,dialog:AddSamplesBinding,alertDialog:AlertDialog) {
        val date = Date(System.currentTimeMillis())
        val dateSample = SimpleDateFormat("yyyy.MM.dd").format(date)

        val hourSample = SimpleDateFormat("hh:mm aaa").format(date)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelSample.saveWebSample(
                    SampleBody(
                        analysisList[0].id_analysis,
                        value,
                        selectedParameter.id_parameter,
                        "$dateSample $hourSample",
                        value
                        )
                ).collect { sample ->
                    when (sample) {
                        is Result.Loading -> {
                            dialog.progressBar?.show()
                            dialog.btnSample?.hide()
                        }
                        is Result.Success -> {
                            dialog.btnSample?.show()
                            dialog.progressBar?.hide()
                            if (sample.data.status == "error"){
                                Snackbar.make(binding.root,"Este parametro solo se puede registrar una vez",Snackbar.LENGTH_SHORT).show()
                            }
                            alertDialog.dismiss()
                            obtainSamples()

                        }
                        is Result.Failure -> {
                            dialog.btnSample?.show()
                            dialog.progressBar?.hide()
                            Snackbar.make(
                                binding.root,
                                "Error al registrarse",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            Log.e("Error", "sendUser: ${sample.exception}")
                        }
                    }
                }
            }
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when(checkedId){
            R.id.cbNaturalWater     -> {waterType=1}
            R.id.cbTreatedWater     -> {waterType=2}
            R.id.cbResidualWater    -> {waterType=3}
            R.id.cbOtherWater       -> {waterType=4}
        }
    }
}