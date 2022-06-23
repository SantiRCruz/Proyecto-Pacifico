package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menuModules.waterUser

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleByAnalysisResponse
import com.fabricaswsenactpi.com.construyendopacifico.databinding.ItemAnalizedParametersWaterBinding

class WaterSamplesAdapter(private val sampleList: List<SampleByAnalysisResponse>,private val context:Context) :
    RecyclerView.Adapter<WaterSamplesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterSamplesViewHolder {
        val itemBinding = ItemAnalizedParametersWaterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WaterSamplesViewHolder(itemBinding,context)
    }

    override fun onBindViewHolder(holder: WaterSamplesViewHolder, position: Int) {
        holder.bind(sampleList[position])
    }

    override fun getItemCount(): Int = sampleList.size
}

class WaterSamplesViewHolder(private val binding: ItemAnalizedParametersWaterBinding,private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(sample: SampleByAnalysisResponse) {
        var measures  = ""
        sample.measure.forEachIndexed { index, measure ->
            measures = "$measures${measure.value},   "
        }
        binding.txtAverage.text = sample.average
        binding.txtParameter.text = sample.parameter_name
        binding.txtLimit.text = "${sample.operator} ${sample.expected_value}"
        binding.txtMeasures.text = measures
        if (sample.id_parameter !=2){
            when(sample.operator){
                "<=" -> {if (sample.expected_value.toFloat() <= sample.average.toFloat()) binding.txtAverage.setTextColor(Color.RED)}
                "="  -> {if (sample.expected_value.toFloat() == sample.average.toFloat()) binding.txtAverage.setTextColor(Color.RED)}
                "<"  -> {if (sample.expected_value.toFloat() <  sample.average.toFloat())  binding.txtAverage.setTextColor(Color.RED)}
            }
        }else{
            if (sample.expected_value != sample.average) binding.txtAverage.setTextColor(Color.RED)
        }
    }
}
