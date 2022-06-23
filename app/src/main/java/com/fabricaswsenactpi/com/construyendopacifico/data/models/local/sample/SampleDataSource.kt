package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.sample

import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.WebService
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.SamplesEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleByAnalysisResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleResponse
import retrofit2.http.Path

class SampleDataSource(private val dao:SampleDao,private val webService: WebService) {
    suspend fun getAllSamples():List<SamplesEntity> = dao.getAllSamples()
    suspend fun saveSamples(samplesEntity: SamplesEntity):Long = dao.saveSamples(samplesEntity)
    suspend fun saveSampleAndMeasure(sampleBody: SampleBody): BaseResponse<SampleResponse> = webService.saveSampleAndMeasure(sampleBody)
    suspend fun getAllSamplesByAnalysis(id_analysis:Int):BaseResponse<SampleByAnalysisResponse> = webService.getAllSamplesByAnalysis(id_analysis)

}