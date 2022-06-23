package com.fabricaswsenactpi.com.construyendopacifico.domain.ethnicgroup

import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.EthnicGroupResponse

interface EthnicGroupRepo {
    suspend fun getEthnicGroups(): BaseResponse<EthnicGroupResponse>

}