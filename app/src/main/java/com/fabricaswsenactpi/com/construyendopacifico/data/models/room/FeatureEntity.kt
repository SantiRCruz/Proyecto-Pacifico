package com.fabricaswsenactpi.com.construyendopacifico.data.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FeatureEntity(
    @PrimaryKey(autoGenerate = false)
    var id_feature: Int,
    var feature_name: String
)
