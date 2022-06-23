package com.fabricaswsenactpi.com.construyendopacifico.data.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PopulationTypesEntity(
    @PrimaryKey(autoGenerate = false)
    val id_population_type: Int,
    val population_type_name: String
)
