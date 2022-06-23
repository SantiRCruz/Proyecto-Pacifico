package com.fabricaswsenactpi.com.construyendopacifico.data.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DepartmentEntity(
    @PrimaryKey(autoGenerate = false)
    val id_department: Int,
    val department_name: String
)