package com.fabricaswsenactpi.com.construyendopacifico.data.models.web



data class BaseResponse<T>(
    val status : String = "",
    val action : String = "",
    val show : String = "",
    val message : String = "",
    val delay : String = "",
    val code : String = "",
    val results : List<T> = listOf()
)