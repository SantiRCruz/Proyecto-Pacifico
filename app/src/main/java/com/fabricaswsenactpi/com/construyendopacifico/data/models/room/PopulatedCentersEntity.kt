package com.fabricaswsenactpi.com.construyendopacifico.data.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PopulatedCentersEntity(
    @PrimaryKey(autoGenerate = false)
    val id_populated_center : Int ,
    val municipality_id : Int ,
    val populated_center_name : String ,
    val populated_center_type : String ,
    )
