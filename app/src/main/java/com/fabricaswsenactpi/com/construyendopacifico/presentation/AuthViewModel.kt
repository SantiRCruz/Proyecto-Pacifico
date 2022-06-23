package com.fabricaswsenactpi.com.construyendopacifico.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.authentication.AuthBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.authentication.AuthResponse
import com.fabricaswsenactpi.com.construyendopacifico.domain.auth.AuthRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import kotlinx.coroutines.flow.stateIn

class AuthViewModel(private val repo: AuthRepo) : ViewModel() {
    fun signIn(authBody: AuthBody): StateFlow<Result<BaseResponse<AuthResponse>>> = flow {
        kotlin.runCatching {
            repo.signIn(authBody)
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

class AuthViewModelFactory(private val repo: AuthRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AuthRepo::class.java).newInstance(repo)
    }

}