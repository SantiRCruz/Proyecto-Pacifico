package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.municipalities

import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.WebService
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.MunicipalitiesEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.MunicipalitiesResponse

class MunicipalitiesDataSource(private val webService: WebService, private val dao : MunicipalitiesDao) {
    suspend fun getAllMunicipalities():BaseResponse<MunicipalitiesResponse> = webService.getAllMunicipalities()
    suspend fun getMunicipalities(id_department:Int): BaseResponse<MunicipalitiesResponse> = webService.getMunicipalities(id_department = id_department)
    suspend fun saveDBMunicipalities(municipalitiesEntity: MunicipalitiesEntity):Long = dao.saveDBMunicipalities(municipalitiesEntity)
}