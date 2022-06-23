package com.fabricaswsenactpi.com.construyendopacifico.domain.analysis

import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.analysis.AnalysisDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.analysis.AnalysisBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.analysis.AnalysisResponse

class AnalysisRepoImpl(private val dataSource: AnalysisDataSource):AnalysisRepo {
    override suspend fun getAnalysisByPopulation(id_population: Int): BaseResponse<AnalysisResponse>  = dataSource.getAnalysisByPopulation(id_population)
    override suspend fun saveAnalysis(analysisBody: AnalysisBody): BaseResponse<AnalysisResponse> = dataSource.saveAnalysis(analysisBody)
}