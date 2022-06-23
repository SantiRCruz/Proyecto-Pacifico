package com.fabricaswsenactpi.com.construyendopacifico.data.models.web.authentication

data class AuthResponse(
    val email: String,
    val id_user: Int,
    val identification: String,
    val phone_number: String,
    val profile: Profile,
    val profile_id: Int,
    val user_last_names: String,
    val user_nick: String,
    val usernames: String
)