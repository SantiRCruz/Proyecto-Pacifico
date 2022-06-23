package com.fabricaswsenactpi.com.construyendopacifico.domain.populatedcenter

import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.PopulatedCentersEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.PopulatedCentersResponse

interface PopulatedCenterRepo {
    suspend fun getPopulatedCenter(id_municipality : Int): BaseResponse<PopulatedCentersResponse>
    suspend fun getAllPopulatedCenter():BaseResponse<PopulatedCentersResponse>
    suspend fun saveDBPopulatedCenter(populatedCentersEntity: PopulatedCentersEntity):Long
}