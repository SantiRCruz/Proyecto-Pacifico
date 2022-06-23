package com.fabricaswsenactpi.com.construyendopacifico.domain.sample

import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.sample.SampleDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.SamplesEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleByAnalysisResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleResponse

class SampleRepoImpl(private val dataSource: SampleDataSource):SampleRepo {
    override suspend fun getAllSamples(): List<SamplesEntity> = dataSource.getAllSamples()
    override suspend fun saveSamples(samplesEntity: SamplesEntity): Long  = dataSource.saveSamples(samplesEntity)
    override suspend fun saveSampleAndMeasure(sampleBody: SampleBody): BaseResponse<SampleResponse> = dataSource.saveSampleAndMeasure(sampleBody)
    override suspend fun getAllSamplesByAnalysis(id_analysis: Int): BaseResponse<SampleByAnalysisResponse> = dataSource.getAllSamplesByAnalysis(id_analysis)
}