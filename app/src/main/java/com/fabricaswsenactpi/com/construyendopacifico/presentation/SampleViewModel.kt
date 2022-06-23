package com.fabricaswsenactpi.com.construyendopacifico.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.SamplesEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleByAnalysisResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.sample.SampleResponse
import com.fabricaswsenactpi.com.construyendopacifico.domain.sample.SampleRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class SampleViewModel(private val repo : SampleRepo):ViewModel() {

    fun fetchSamples(): StateFlow<Result<List<SamplesEntity>>> = flow {
        kotlin.runCatching {
            repo.getAllSamples()
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
    fun saveSamples(samplesEntity: SamplesEntity):StateFlow<Result<Long>> = flow {
        kotlin.runCatching {
            repo.saveSamples(samplesEntity)
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

    //////  WEB ///////

    fun saveWebSample(sampleBody:SampleBody):StateFlow<Result<BaseResponse<SampleResponse>>> = flow {
        kotlin.runCatching {
            repo.saveSampleAndMeasure(sampleBody)
        }.onSuccess {
            emit(Result.Success(it))
        }.onFailure {
            emit(Result.Failure(Exception(it)))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
         initialValue = Result.Loading()
    )
    fun fetchSamplesByAnalysis(id_analysis:Int):StateFlow<Result<BaseResponse<SampleByAnalysisResponse>>> = flow {
        kotlin.runCatching {
            repo.getAllSamplesByAnalysis(id_analysis)
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

class SampleViewModelFactory(private val repo : SampleRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SampleRepo::class.java).newInstance(repo)
    }
}