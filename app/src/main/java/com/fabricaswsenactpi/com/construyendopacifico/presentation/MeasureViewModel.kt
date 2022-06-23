package com.fabricaswsenactpi.com.construyendopacifico.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.MeasureEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.SamplesEntity
import com.fabricaswsenactpi.com.construyendopacifico.domain.measure.MeasureRepo
import com.fabricaswsenactpi.com.construyendopacifico.domain.sample.SampleRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MeasureViewModel(private val repo : MeasureRepo):ViewModel() {
    fun fetchMeasure(sample_id:String): StateFlow<Result<List<MeasureEntity>>> = flow {
        kotlin.runCatching {
            repo.getAllMeasure(sample_id)
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
    fun saveMeasure(measureEntity: MeasureEntity): StateFlow<Result<Long>> = flow {
        kotlin.runCatching {
            repo.saveSamples(measureEntity)
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
class MeasureViewModelFactory(private val repo : MeasureRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MeasureRepo::class.java).newInstance(repo)
    }
}