package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.parameter

import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.WebService
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.ParameterResponse

class ParameterDataSource(private val webService: WebService) {
    suspend fun getParameters(): BaseResponse<ParameterResponse> = webService.getParameters()
}