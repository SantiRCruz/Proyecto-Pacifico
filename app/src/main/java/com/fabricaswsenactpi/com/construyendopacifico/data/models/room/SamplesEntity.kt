package com.fabricaswsenactpi.com.construyendopacifico.data.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SamplesEntity(
    @PrimaryKey(autoGenerate = true)
    val id_sample: Int,
    val parameter_id: Int,
    val analysis_id: Int,
    val average: String,
)
