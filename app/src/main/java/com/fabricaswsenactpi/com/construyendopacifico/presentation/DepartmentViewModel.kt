package com.fabricaswsenactpi.com.construyendopacifico.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.DepartmentEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.DepartmentResponse
import com.fabricaswsenactpi.com.construyendopacifico.domain.department.DepartmentRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class DepartmentViewModel(private val repo :DepartmentRepo):ViewModel() {
    fun fetchDepartments(): StateFlow<Result<BaseResponse<DepartmentResponse>>> = flow {
        kotlin.runCatching {
            repo.getDepartments()
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
    fun fetchDBDepartments():StateFlow<Result<List<DepartmentEntity>>> = flow {
        kotlin.runCatching {
            repo.getAllDBDepartments()
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
     fun saveDBDepartment(departmentEntity: DepartmentEntity):StateFlow<Result<Long>> = flow {
         kotlin.runCatching {
             repo.saveDBDepartment(departmentEntity)
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

class DepartmentViewModelFactory(private val repo : DepartmentRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DepartmentRepo::class.java).newInstance(repo)
    }
}