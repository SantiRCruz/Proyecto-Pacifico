package com.fabricaswsenactpi.com.construyendopacifico.domain.population

import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.population.PopulationDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.PopulationsEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population.PopulationBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population.PopulationResponse

class PopulationRepoImpl(private val dataSource: PopulationDataSource):PopulationRepo {
    override suspend fun getAllPopulations(): List<PopulationsEntity> = dataSource.getAllPopulations()
    override suspend fun savePopulation(populationsEntity: PopulationsEntity): Long = dataSource.savePopulation(populationsEntity)
    override suspend fun deletePopulation(populationsEntity: PopulationsEntity):Int = dataSource.deletePopulation(populationsEntity)
    override suspend fun updateUser(populationsEntity: PopulationsEntity): Int = dataSource.updateUser(populationsEntity)
    override suspend fun saveWebPopulation(populationBody: PopulationBody): BaseResponse<PopulationResponse> = dataSource.saveWebPopulation(populationBody)
    override suspend fun updateWebPopulation(populationBody: PopulationBody, idPopulation:Int):BaseResponse<PopulationResponse> = dataSource.updateWebPopulation(populationBody,idPopulation)
    override suspend fun getAllWebPopulations(): BaseResponse<PopulationResponse> =  dataSource.getAllWebPopulations()
    override suspend fun deleteWebPopulation(id_population: Int): BaseResponse<String> = dataSource.deleteWebPopulation(id_population)
}
