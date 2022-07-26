package com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PopulationResponse(
    val altitude: String,
//    val catchment_type: String,
    val created_at: String,
    val department_id: Int,
//    val ethnic_group_id: Int,
//    val ethnic_group_name: String,
//    val id_ethnic_group: Int,
    val id_municipality: Int,
    val id_populated_center: Int,
    val id_population: Int,
    val inhabitants_number: String,
    val latitude: String,
    val length: String,
    val municipality_id: Int,
    val municipality_name: String,
    val photography: String,
    val populated_center_id: Int,
    val populated_center_name: String,
    val populated_center_type: String,
//    val surface_sources: String,
//    val underground_sources: String,
    val updated_at: String,
    val id_department: Int,
    val department_name: String
):Parcelable