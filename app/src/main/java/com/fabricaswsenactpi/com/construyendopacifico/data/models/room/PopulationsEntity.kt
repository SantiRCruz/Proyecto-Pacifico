package com.fabricaswsenactpi.com.construyendopacifico.data.models.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class PopulationsEntity(
    @PrimaryKey(autoGenerate = true)
    val id_population: Int,
    val populated_center_id: Int,
    val ethnic_group_id: Int,
    val length: String,
    val latitude: String,
    val altitude: String,
    val photography: String,
    val inhabitants_number: String,
    val surface_sources: String,
    val underground_sources: String,
    val catchment_type: String,
):Parcelable
