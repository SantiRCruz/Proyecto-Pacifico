package com.fabricaswsenactpi.com.construyendopacifico.data.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaterTypesEntity(
    @PrimaryKey(autoGenerate = false)
    val id_water_type: Int,
    val water_type_name: String
)