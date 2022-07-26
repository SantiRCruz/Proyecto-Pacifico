package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.EthnicGroupResponse
import com.fabricaswsenactpi.com.construyendopacifico.databinding.ItemEthnicGroupsBinding
import com.google.android.material.snackbar.Snackbar

class EthnicGroupAdapter(private val ethnicGroups:List<EthnicGroupResponse>):RecyclerView.Adapter<EthnicGroupAdapter.EthnicGroupViewHolder>() {
    private val ethnicArray = mutableListOf<EthnicGroupResponse>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EthnicGroupViewHolder {
        val binding = ItemEthnicGroupsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EthnicGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EthnicGroupViewHolder, position: Int) {
        holder.bind(ethnicGroups[position])
    }

    override fun getItemCount(): Int = ethnicGroups.size

    inner class EthnicGroupViewHolder(private val binding:ItemEthnicGroupsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(ethnicGroups: EthnicGroupResponse){
            ethnicArray.add(ethnicGroups)
            binding.txtName.text = ethnicGroups.ethnic_group_name
            binding.imgClose.setOnClickListener {
                updateData(ethnicGroups)
            }
        }
    }
    fun updateData(item:EthnicGroupResponse){
        ethnicArray.remove(item)
    }
    fun updateInfo():MutableList<EthnicGroupResponse>{
        return ethnicArray
    }

}
