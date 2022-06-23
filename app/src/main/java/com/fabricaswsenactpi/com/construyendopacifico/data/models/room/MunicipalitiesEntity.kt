package com.fabricaswsenactpi.com.construyendopacifico.data.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MunicipalitiesEntity(
    @PrimaryKey(autoGenerate = false)
    val id_municipality: Int,
    val department_id: Int,
    val municipality_name: String
)
