package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.populatedcenter

import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.WebService
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.PopulatedCentersEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.PopulatedCentersResponse
import retrofit2.http.Path

class PopulatedCenterDataSource(private val webService: WebService,private val dao : PopulatedCenterDao) {
    suspend fun getPopulatedCenter(id_municipality : Int): BaseResponse<PopulatedCentersResponse> =  webService.getPopulatedCenter(id_municipality)
    suspend fun getAllPopulatedCenter():BaseResponse<PopulatedCentersResponse> =  webService.getAllPopulatedCenter()
    suspend fun saveDBPopulatedCenter(populatedCentersEntity: PopulatedCentersEntity):Long = dao.saveDBPopulatedCenter(populatedCentersEntity)
}