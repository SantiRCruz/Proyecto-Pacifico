package com.fabricaswsenactpi.com.construyendopacifico.domain.analysis

import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.analysis.AnalysisBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.analysis.AnalysisResponse
import retrofit2.http.Path

interface AnalysisRepo {
    suspend fun getAnalysisByPopulation(id_population:Int): BaseResponse<AnalysisResponse>
    suspend fun saveAnalysis(analysisBody: AnalysisBody):BaseResponse<AnalysisResponse>
}