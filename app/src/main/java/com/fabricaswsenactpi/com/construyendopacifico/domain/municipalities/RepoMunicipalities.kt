package com.fabricaswsenactpi.com.construyendopacifico.domain.municipalities

import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.MunicipalitiesEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.MunicipalitiesResponse

interface RepoMunicipalities {
    suspend fun getAllMunicipalities():BaseResponse<MunicipalitiesResponse>
    suspend fun getMunicipalities(id_department:Int):BaseResponse<MunicipalitiesResponse>
    suspend fun saveDBMunicipalities(municipalitiesEntity: MunicipalitiesEntity):Long
}