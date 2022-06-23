package com.fabricaswsenactpi.com.construyendopacifico.domain.sample

import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.SamplesEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleByAnalysisResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleResponse

interface SampleRepo {
    suspend fun getAllSamples():List<SamplesEntity>
    suspend fun saveSamples(samplesEntity: SamplesEntity):Long
    suspend fun saveSampleAndMeasure(sampleBody: SampleBody): BaseResponse<SampleResponse>
    suspend fun getAllSamplesByAnalysis(id_analysis:Int):BaseResponse<SampleByAnalysisResponse>
}