package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menu.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.databinding.ItemMenuZonesBinding
import com.fabricaswsenactpi.com.construyendopacifico.presentation.PopulationViewModel
import com.fabricaswsenactpi.com.construyendopacifico.ui.user.menu.ZonesMenuDirections
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.core.hide
import com.fabricaswsenactpi.com.construyendopacifico.core.show
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population.PopulationResponse
import com.fabricaswsenactpi.com.construyendopacifico.databinding.ConfirmDeleteZoneBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ZonesMenuAdapter(
    private val populationList: List<PopulationResponse>,
    private val viewModel: PopulationViewModel,
    private val context: Context
) :
    RecyclerView.Adapter<ZonesMenuAdapter.ZonesMenuViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ZonesMenuAdapter.ZonesMenuViewHolder {
        val itemBinding =
            ItemMenuZonesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZonesMenuViewHolder(itemBinding, viewModel,context)
    }

    override fun onBindViewHolder(holder: ZonesMenuAdapter.ZonesMenuViewHolder, position: Int) {
        holder.bind(populationList[position])
    }

    override fun getItemCount(): Int = populationList.size

    inner class ZonesMenuViewHolder(
        private val binding: ItemMenuZonesBinding,
        private val viewModel: PopulationViewModel,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(population: PopulationResponse) {
            binding.txtNameMunicipalities.text = population.municipality_name
            binding.txtVereda.text = population.populated_center_name
            binding.btnNextProcessZone.setOnClickListener {
                val action = ZonesMenuDirections.actionZonesMenuToMenuModules2(population)
                Navigation.findNavController(binding.root).navigate(action)
            }
            binding.btnEditZone.setOnClickListener {
                val action = ZonesMenuDirections.actionZonesMenuToZonesFormUpdate(population)
                Navigation.findNavController(binding.root).navigate(action)
            }
            binding.btnDeleteZone.setOnClickListener {
                val dialogBinding = ConfirmDeleteZoneBinding.inflate(LayoutInflater.from(context))

                val dialog = AlertDialog.Builder(context).apply {
                    setView(dialogBinding.root)
                }.create()
//        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

                dialogBinding.textView8.text = "Desea eliminar esta zona?"
                dialogBinding.textView8.setTextColor(R.color.blueDarkSena)

                dialogBinding.idBtnCancelar.setOnClickListener { dialog.dismiss() }
                dialogBinding.idBtnEliminarZona.setOnClickListener {
                    val scope = CoroutineScope(Dispatchers.Main)
                    scope.launch {
                        viewModel.deleteWebPopulation(population.id_population).collect {
                            when (it) {
                                is Result.Loading -> {
                                    binding.progressBar.show()
                                    binding.options.hide()
                                }
                                is Result.Success -> {
                                    binding.options.show()
                                    binding.progressBar.hide()
                                    Snackbar.make(
                                        binding.root,
                                        "Eliminado correctamente",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                    Navigation.findNavController(binding.root).navigate(R.id.action_zonesMenu_self)
                                }
                                is Result.Failure -> {
                                    binding.options.show()
                                    binding.progressBar.hide()
                                    Snackbar.make(
                                        binding.root,
                                        "Error al eliminar los datos",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                    Log.e("Error", "bind: ${it.exception}")
                                }
                            }
                        }
                    }
                    dialog.dismiss()
                }
                dialog.setCancelable(false)
                dialog.show()
            }
        }

    }
}
