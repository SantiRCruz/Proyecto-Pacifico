package com.fabricaswsenactpi.com.construyendopacifico.domain.ethnicgroup

import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.ethnicgroup.EthnicGroupDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.EthnicGroupResponse

class EthnicGroupRepoImpl(private val dataSource:EthnicGroupDataSource):EthnicGroupRepo {
    override suspend fun getEthnicGroups(): BaseResponse<EthnicGroupResponse> = dataSource.getEthnicGroups()
}