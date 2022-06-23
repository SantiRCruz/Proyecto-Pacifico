package com.fabricaswsenactpi.com.construyendopacifico.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.MunicipalitiesEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.MunicipalitiesResponse
import com.fabricaswsenactpi.com.construyendopacifico.domain.municipalities.RepoMunicipalities
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MunicipalitiesViewModel(private val repo :RepoMunicipalities):ViewModel() {

    fun fetchAllMunicipalities():StateFlow<Result<BaseResponse<MunicipalitiesResponse>>> = flow {
        kotlin.runCatching {
            repo.getAllMunicipalities()
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

    fun fetchMunicipalities(id_department:Int): StateFlow<Result<BaseResponse<MunicipalitiesResponse>>> = flow {
        kotlin.runCatching {
            repo.getMunicipalities(id_department)
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
    fun saveDBMunicipalities(municipalitiesEntity: MunicipalitiesEntity):StateFlow<Result<Long>> = flow {
        kotlin.runCatching {
            repo.saveDBMunicipalities(municipalitiesEntity)
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
class MunicipalitiesViewModelFactory(private val repo : RepoMunicipalities): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RepoMunicipalities::class.java).newInstance(repo)
    }
}