package com.fabricaswsenactpi.com.construyendopacifico.domain.auth

import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.auth.AuthDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.authentication.AuthBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.authentication.AuthResponse


class AuthRepoImpl(private val dataSource:AuthDataSource ):AuthRepo {
    override suspend fun signIn(authBody: AuthBody): BaseResponse<AuthResponse> = dataSource.signIn(authBody)
}