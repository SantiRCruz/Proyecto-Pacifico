package com.fabricaswsenactpi.com.construyendopacifico.domain.parameter

import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.ParameterResponse

interface ParameterRepo {
    suspend fun getParameters(): BaseResponse<ParameterResponse>
}