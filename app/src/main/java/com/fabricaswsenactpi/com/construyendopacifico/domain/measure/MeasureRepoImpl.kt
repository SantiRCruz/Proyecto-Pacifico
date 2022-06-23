package com.fabricaswsenactpi.com.construyendopacifico.domain.measure

import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.measure.MeasureDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.sample.SampleDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.MeasureEntity

class MeasureRepoImpl(private val dataSource: MeasureDataSource):MeasureRepo {
    override suspend fun getAllMeasure(sample_id: String): List<MeasureEntity>  = dataSource.getAllMeasure(sample_id)
    override suspend fun saveSamples(measureEntity: MeasureEntity): Long = dataSource.saveSamples(measureEntity)
}