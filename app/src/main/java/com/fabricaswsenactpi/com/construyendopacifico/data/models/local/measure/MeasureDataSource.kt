package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.measure

import android.icu.util.Measure
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.MeasureEntity

class MeasureDataSource (private val dao : MeasureDao){
    suspend fun getAllMeasure(sample_id:String):List<MeasureEntity> = dao.getAllMeasure(sample_id)
    suspend fun saveSamples(measureEntity: MeasureEntity):Long = dao.saveSamples(measureEntity)
}