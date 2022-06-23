package com.fabricaswsenactpi.com.construyendopacifico.data.models.web

data class PopulatedCentersResponse(
    val id_populated_center: Int,
    val municipality_id: Int,
    val populated_center_name: String,
    val populated_center_type: String,
    val id_municipality: Int,
    val department_id: Int,
    val municipality_name: String
)
