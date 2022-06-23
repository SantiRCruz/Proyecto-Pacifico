package com.fabricaswsenactpi.com.construyendopacifico.data.models.web

data class UserResponse(
    var id_user: Int,
    var usernames: String,
    var user_last_names: String,
    var user_nick: String,
    var phone_number: String,
    var identification: String,
    var email: String,
    var name_profile: String,
    var profile_id: Int
)