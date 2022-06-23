package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.population

import androidx.room.*
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.PopulationsEntity

@Dao
interface PopulationDao {

    @Query("SELECT * FROM populationsentity")
    suspend fun getAllPopulations():List<PopulationsEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun savePopulation(populationsEntity: PopulationsEntity):Long

    @Update
    suspend fun updateUser(populationsEntity: PopulationsEntity):Int

    @Delete
    suspend fun deletePopulation(populationsEntity: PopulationsEntity):Int
}