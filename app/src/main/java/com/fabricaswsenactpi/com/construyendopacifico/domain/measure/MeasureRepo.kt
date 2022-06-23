package com.fabricaswsenactpi.com.construyendopacifico.domain.measure

import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.MeasureEntity

interface MeasureRepo {
    suspend fun getAllMeasure(sample_id:String):List<MeasureEntity>
    suspend fun saveSamples(measureEntity: MeasureEntity):Long
}