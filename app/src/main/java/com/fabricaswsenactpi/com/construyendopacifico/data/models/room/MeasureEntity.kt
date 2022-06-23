package com.fabricaswsenactpi.com.construyendopacifico.data.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MeasureEntity(
    @PrimaryKey(autoGenerate = true)
    val id_measure: Int,
    val sample_id: Int,
    val value: String,
    val register_date: String,
)
