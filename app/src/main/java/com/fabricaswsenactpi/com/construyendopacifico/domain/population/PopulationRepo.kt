package com.fabricaswsenactpi.com.construyendopacifico.domain.population

import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.PopulationsEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population.PopulationBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population.PopulationResponse

interface PopulationRepo {
    suspend fun getAllPopulations():List<PopulationsEntity>
    suspend fun savePopulation(populationsEntity: PopulationsEntity):Long
    suspend fun deletePopulation(populationsEntity: PopulationsEntity):Int
    suspend fun updateUser(populationsEntity: PopulationsEntity):Int
    suspend fun saveWebPopulation(populationBody: PopulationBody): BaseResponse<PopulationResponse>
    suspend fun getAllWebPopulations():BaseResponse<PopulationResponse>
    suspend fun deleteWebPopulation(id_population:Int):BaseResponse<String>
    suspend fun updateWebPopulation(populationBody: PopulationBody, idPopulation:Int):BaseResponse<PopulationResponse>

}