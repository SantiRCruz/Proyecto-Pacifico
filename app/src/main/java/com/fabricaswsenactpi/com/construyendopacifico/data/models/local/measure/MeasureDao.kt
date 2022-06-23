package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.measure

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.MeasureEntity

@Dao
interface MeasureDao {
    @Query("SELECT * FROM measureentity where sample_id = :sample_id")
    suspend fun getAllMeasure(sample_id:String):List<MeasureEntity>

    @Insert(onConflict = IGNORE)
    suspend fun saveSamples(measureEntity: MeasureEntity):Long
}