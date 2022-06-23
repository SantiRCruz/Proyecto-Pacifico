package com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample

data class SampleByAnalysisResponse(
    val analysis_id: Int,
    val average: String,
    val expected_value: String,
    val feature_id: Int,
    val id_parameter: Int,
    val id_sample: Int,
    val measure: List<Measure>,
    val `operator`: String,
    val parameter_id: Int,
    val parameter_name: String,
    val sample_type_id: Int,
    val units: String
)