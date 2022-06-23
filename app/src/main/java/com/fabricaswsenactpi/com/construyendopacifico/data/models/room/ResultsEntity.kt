package com.fabricaswsenactpi.com.construyendopacifico.data.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResultsEntity(
    @PrimaryKey(autoGenerate = false)
    val id_department: Int,
    val analysis_id: Int,
    val parameter_id: Int
)
