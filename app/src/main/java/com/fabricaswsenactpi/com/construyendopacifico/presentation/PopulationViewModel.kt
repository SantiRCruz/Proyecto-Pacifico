package com.fabricaswsenactpi.com.construyendopacifico.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.PopulationsEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population.PopulationBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.population.PopulationResponse
import com.fabricaswsenactpi.com.construyendopacifico.domain.population.PopulationRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class PopulationViewModel(private val repo:PopulationRepo):ViewModel() {
    fun fetchPopulations(): StateFlow<Result<List<PopulationsEntity>>> = flow {
        kotlin.runCatching {
            repo.getAllPopulations()
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

    fun savePopulation(populationsEntity: PopulationsEntity):StateFlow<Result<Long>> = flow {
        kotlin.runCatching {
            repo.savePopulation(populationsEntity)
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

    fun deletePopulation(populationsEntity: PopulationsEntity):StateFlow<Result<Int>> = flow {
        kotlin.runCatching {
            repo.deletePopulation(populationsEntity)
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
    fun updatePopulation(populationsEntity: PopulationsEntity):StateFlow<Result<Int>> = flow {
        kotlin.runCatching {
            repo.updateUser(populationsEntity)
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

    // WEB //

    fun saveWebPopulation(populationBody: PopulationBody):StateFlow<Result<BaseResponse<PopulationResponse>>> = flow {
        kotlin.runCatching {
            repo.saveWebPopulation(populationBody)
        }.onSuccess {
            emit(Result.Success(it))
        }.onFailure {
            emit(Result.Failure(Exception(it.message.toString())))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading()
    )

    fun updateWebPopulation(populationBody: PopulationBody,idPopulation: Int):StateFlow<Result<BaseResponse<PopulationResponse>>> = flow {
        kotlin.runCatching {
            repo.updateWebPopulation(populationBody,idPopulation)
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
    fun fetchWebPopulations():StateFlow<Result<BaseResponse<PopulationResponse>>> = flow{
        kotlin.runCatching {
            repo.getAllWebPopulations()
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
    fun deleteWebPopulation(idPopulation:Int):StateFlow<Result<BaseResponse<String>>> = flow {
        kotlin.runCatching {
            repo.deleteWebPopulation(idPopulation)
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
class PopulationViewModelFactory(private val repo : PopulationRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PopulationRepo::class.java).newInstance(repo)
    }
}