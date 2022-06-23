package com.fabricaswsenactpi.com.construyendopacifico.domain.auth

import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.authentication.AuthBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.authentication.AuthResponse

interface AuthRepo {
    suspend fun signIn( authBody: AuthBody): BaseResponse<AuthResponse>
}