package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.populatedcenter

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.PopulatedCentersEntity

@Dao
interface PopulatedCenterDao {
    @Insert(onConflict = ABORT)
    suspend fun saveDBPopulatedCenter(populatedCentersEntity: PopulatedCentersEntity):Long
}