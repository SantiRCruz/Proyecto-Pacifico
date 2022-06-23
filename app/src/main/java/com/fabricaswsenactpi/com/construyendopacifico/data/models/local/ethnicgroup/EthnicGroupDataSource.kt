package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.ethnicgroup

import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.WebService
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.EthnicGroupResponse

class EthnicGroupDataSource (private val webService: WebService){
    suspend fun getEthnicGroups(): BaseResponse<EthnicGroupResponse> = webService.getEthnicGroups()
}