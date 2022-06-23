package com.fabricaswsenactpi.com.construyendopacifico.ui.user.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fabricaswsenactpi.com.construyendopacifico.databinding.ItemEthnicGroupsBinding

class EthnicGroupAdapter(private val ethnicGroups:List<String>):RecyclerView.Adapter<EthnicGroupAdapter.EthnicGroupViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EthnicGroupViewHolder {
        val binding = ItemEthnicGroupsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EthnicGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EthnicGroupViewHolder, position: Int) {
        holder.bind(ethnicGroups[position])
    }

    override fun getItemCount(): Int = ethnicGroups.size

    inner class EthnicGroupViewHolder(private val binding:ItemEthnicGroupsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(ethnicGroups: String){
            binding.txtName.text = ethnicGroups
        }
    }
}
