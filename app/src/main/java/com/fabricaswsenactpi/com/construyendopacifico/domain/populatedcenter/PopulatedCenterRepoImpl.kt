package com.fabricaswsenactpi.com.construyendopacifico.domain.populatedcenter

import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.populatedcenter.PopulatedCenterDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.PopulatedCentersEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.PopulatedCentersResponse

class PopulatedCenterRepoImpl(private val dataSource:PopulatedCenterDataSource):PopulatedCenterRepo {
    override suspend fun getPopulatedCenter(id_municipality: Int): BaseResponse<PopulatedCentersResponse> = dataSource.getPopulatedCenter(id_municipality)
    override suspend fun getAllPopulatedCenter(): BaseResponse<PopulatedCentersResponse> = dataSource.getAllPopulatedCenter()
    override suspend fun saveDBPopulatedCenter(populatedCentersEntity: PopulatedCentersEntity): Long = dataSource.saveDBPopulatedCenter(populatedCentersEntity)
}