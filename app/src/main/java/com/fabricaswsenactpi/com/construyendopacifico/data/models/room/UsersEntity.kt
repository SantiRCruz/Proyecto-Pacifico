package com.fabricaswsenactpi.com.construyendopacifico.data.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsersEntity(
    @PrimaryKey(autoGenerate = false)
    val id_user: Int,
    val profile_id: Int,
    val user_nick: String,
    val password: String,
    val usernames: String,
    val user_last_names: String,
    val phone_number: String,
    val email: String,
    val identification: String,
)
