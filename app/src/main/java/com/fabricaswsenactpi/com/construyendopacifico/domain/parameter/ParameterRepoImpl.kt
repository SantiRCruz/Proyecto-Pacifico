package com.fabricaswsenactpi.com.construyendopacifico.domain.parameter

import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.parameter.ParameterDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.ParameterResponse

class ParameterRepoImpl(private val dataSource:ParameterDataSource):ParameterRepo {
    override suspend fun getParameters(): BaseResponse<ParameterResponse> = dataSource.getParameters()
}