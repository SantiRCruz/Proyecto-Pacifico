package com.fabricaswsenactpi.com.construyendopacifico.data.models.web

data class ParameterResponse(
    var id_parameter: Int,
    var feature_id: Int,
    var sample_type_id: Int,
    var parameter_name: String,
    var units: String,
    var expected_value: String,
    var operator: String
)
