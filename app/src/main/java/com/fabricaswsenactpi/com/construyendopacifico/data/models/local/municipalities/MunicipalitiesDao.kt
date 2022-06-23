package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.municipalities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.Query
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.MunicipalitiesEntity

@Dao
interface MunicipalitiesDao {

    @Query("SELECT * FROM MunicipalitiesEntity")
    suspend fun getAllDbMunicipalities():List<MunicipalitiesEntity>

    @Insert(onConflict = ABORT)
    suspend fun saveDBMunicipalities(municipalitiesEntity: MunicipalitiesEntity):Long
}