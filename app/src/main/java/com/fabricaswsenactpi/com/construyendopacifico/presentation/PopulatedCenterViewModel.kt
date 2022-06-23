package com.fabricaswsenactpi.com.construyendopacifico.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.PopulatedCentersEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.MunicipalitiesResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.PopulatedCentersResponse
import com.fabricaswsenactpi.com.construyendopacifico.domain.municipalities.RepoMunicipalities
import com.fabricaswsenactpi.com.construyendopacifico.domain.populatedcenter.PopulatedCenterRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class PopulatedCenterViewModel(private val repo : PopulatedCenterRepo):ViewModel() {
    fun fetchPopulatedCenters(id_municipality:Int): StateFlow<Result<BaseResponse<PopulatedCentersResponse>>> = flow {
        kotlin.runCatching {
            repo.getPopulatedCenter(id_municipality)
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

    fun fetchAllPopulatedCenters():StateFlow<Result<BaseResponse<PopulatedCentersResponse>>> = flow {
        kotlin.runCatching {
            repo.getAllPopulatedCenter()
        }.onSuccess {
            emit(Result.Success(it))
        }.onFailure {
            emit(Result.Failure(Exception((it.message))))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading()
    )

    fun saveDBPopulatedCenter(populatedCentersEntity: PopulatedCentersEntity):StateFlow<Result<Long>> = flow {
        kotlin.runCatching {
            repo.saveDBPopulatedCenter(populatedCentersEntity)
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

class PopulatedCenterViewModelFactory(private val repo : PopulatedCenterRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PopulatedCenterRepo::class.java).newInstance(repo)
    }
}