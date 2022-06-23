package com.fabricaswsenactpi.com.construyendopacifico.data.models.web

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class ResEngine(
    var id_populated_center: Int,
    var municipality_id: String,
    var populated_center_name: String,
    var populated_center_type: String
)