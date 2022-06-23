package com.fabricaswsenactpi.com.construyendopacifico.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.ParameterResponse
import com.fabricaswsenactpi.com.construyendopacifico.domain.parameter.ParameterRepo
import com.fabricaswsenactpi.com.construyendopacifico.domain.population.PopulationRepo
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class ParameterViewModel(private val  repo : ParameterRepo): ViewModel() {

    fun fetchParameter():StateFlow<Result<BaseResponse<ParameterResponse>>> = flow {
        kotlin.runCatching {
            repo.getParameters()
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

}
class ParameterViewModelFactory(private val  repo : ParameterRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ParameterRepo::class.java).newInstance(repo)
    }
}