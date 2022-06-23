package com.fabricaswsenactpi.com.construyendopacifico.domain.municipalities

import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.municipalities.MunicipalitiesDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.MunicipalitiesEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.MunicipalitiesResponse


class RepoMunicipalitiesImpl(private val dataSource: MunicipalitiesDataSource ):RepoMunicipalities {
    override suspend fun getAllMunicipalities(): BaseResponse<MunicipalitiesResponse> = dataSource.getAllMunicipalities()
    override suspend fun getMunicipalities(id_department:Int): BaseResponse<MunicipalitiesResponse> =  dataSource.getMunicipalities(id_department)
    override suspend fun saveDBMunicipalities(municipalitiesEntity: MunicipalitiesEntity): Long  = dataSource.saveDBMunicipalities(municipalitiesEntity)
}