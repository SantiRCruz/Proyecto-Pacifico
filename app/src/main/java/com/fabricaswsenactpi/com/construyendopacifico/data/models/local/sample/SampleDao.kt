package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.sample

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.SamplesEntity

@Dao
interface SampleDao {

    @Query("SELECT * FROM samplesentity")
    suspend fun getAllSamples():List<SamplesEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveSamples(samplesEntity: SamplesEntity):Long
}