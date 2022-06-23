package com.fabricaswsenactpi.com.construyendopacifico.data.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProfilesEntity(
    @PrimaryKey(autoGenerate = false)
    val id_profile: Int,
    val name_profile: String
)
