package com.fabricaswsenactpi.com.construyendopacifico.data.models.web.analysis

data class AnalysisResponse(
    val id_analysis: Int,
    val user_id: Int,
    val population_id: Int,
    val sample_type_id: Int,
    val water_type_id: Int,
    val date: String,
    val hour: String,
    val sample_type: String,
    val surface_sources: String,
    val underground_sources: String,
    val catchment_type: String,
    val created_at: String,
    val updated_at: String,
)