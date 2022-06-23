package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.analysis

import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.WebService
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.analysis.AnalysisBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.analysis.AnalysisResponse

class AnalysisDataSource(private val webService: WebService) {
    suspend fun getAnalysisByPopulation(id_population:Int): BaseResponse<AnalysisResponse> = webService.getAnalysisByPopulation(id_population)
    suspend fun saveAnalysis(analysisBody: AnalysisBody):BaseResponse<AnalysisResponse> = webService.saveAnalysis(analysisBody)
}