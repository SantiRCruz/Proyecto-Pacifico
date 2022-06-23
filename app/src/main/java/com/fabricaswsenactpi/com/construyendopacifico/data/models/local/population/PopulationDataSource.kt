package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.population

import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.WebService
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.PopulationsEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population.PopulationBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population.PopulationResponse
import retrofit2.http.Body
import retrofit2.http.Path

class PopulationDataSource(private val dao: PopulationDao,private val webService: WebService) {
    suspend fun getAllPopulations():List<PopulationsEntity> = dao.getAllPopulations()
    suspend fun savePopulation(populationsEntity: PopulationsEntity):Long = dao.savePopulation(populationsEntity)
    suspend fun deletePopulation(populationsEntity: PopulationsEntity):Int = dao.deletePopulation(populationsEntity)
    suspend fun updateUser(populationsEntity: PopulationsEntity):Int = dao.updateUser(populationsEntity)
    suspend fun saveWebPopulation(populationBody: PopulationBody): BaseResponse<PopulationResponse> = webService.saveWebPopulation(populationBody)
    suspend fun getAllWebPopulations():BaseResponse<PopulationResponse> = webService.getWebPopulations()
    suspend fun deleteWebPopulation(id_population:Int):BaseResponse<String> = webService.deleteWebPopulation(id_population)
    suspend fun updateWebPopulation(populationBody: PopulationBody, idPopulation:Int):BaseResponse<PopulationResponse> = webService.updateWebPopulation(populationBody,idPopulation)
}