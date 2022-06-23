package com.fabricaswsenactpi.com.construyendopacifico.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.DepartmentResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.EthnicGroupResponse
import com.fabricaswsenactpi.com.construyendopacifico.domain.department.DepartmentRepo
import com.fabricaswsenactpi.com.construyendopacifico.domain.ethnicgroup.EthnicGroupRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class EthnicGroupViewModel(private val repo:EthnicGroupRepo):ViewModel() {
    fun fetchDepartments(): StateFlow<Result<BaseResponse<EthnicGroupResponse>>> = flow {
        kotlin.runCatching {
            repo.getEthnicGroups()
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
class EthnicGroupViewModelFactory(private val repo : EthnicGroupRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(EthnicGroupRepo::class.java).newInstance(repo)
    }
}