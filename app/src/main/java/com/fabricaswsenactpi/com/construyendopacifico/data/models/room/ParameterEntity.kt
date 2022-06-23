package com.fabricaswsenactpi.com.construyendopacifico.data.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ParameterEntity(
    @PrimaryKey(autoGenerate = false)
    val id_parameter: Int,
    val feature_id: Int,
    val sample_type_id: Int,
    val parameter_name: String,
    val units: String,
    val expected_value: String,
    val operator: String
)
