package com.fabricaswsenactpi.com.construyendopacifico.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.analysis.AnalysisBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.analysis.AnalysisResponse
import com.fabricaswsenactpi.com.construyendopacifico.domain.analysis.AnalysisRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class AnalysisViewModel(private val repo:AnalysisRepo):ViewModel() {
    fun fetchAnalysisByPopulation(idPopulation:Int):StateFlow<Result<BaseResponse<AnalysisResponse>>> = flow {
        kotlin.runCatching {
            repo.getAnalysisByPopulation(idPopulation)
        }.onSuccess {
            emit(Result.Success(it))
        }.onFailure {
            emit(Result.Failure(Exception(it.message)))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading()
    )

    fun saveAnalysis(analysisBody: AnalysisBody):StateFlow<Result<BaseResponse<AnalysisResponse>>> = flow {
        kotlin.runCatching {
            repo.saveAnalysis(analysisBody)
        }.onSuccess {
            emit(Result.Success(it))
        }.onFailure {
            emit(Result.Failure(Exception(it.message)))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading()
    )
}
class AnalysisViewModelFactory(private val repo:AnalysisRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AnalysisRepo::class.java).newInstance(repo)
    }
}