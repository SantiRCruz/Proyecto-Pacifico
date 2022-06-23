package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.auth

import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.WebService
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.authentication.AuthBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.authentication.AuthResponse

class AuthDataSource(private val webService: WebService) {
    suspend fun signIn(authBody: AuthBody): BaseResponse<AuthResponse> = webService.signIn(authBody)
}