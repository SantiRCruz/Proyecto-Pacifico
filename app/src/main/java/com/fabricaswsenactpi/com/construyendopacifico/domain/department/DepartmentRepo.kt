package com.fabricaswsenactpi.com.construyendopacifico.domain.department

import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.DepartmentEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.DepartmentResponse

interface DepartmentRepo {
    suspend fun getDepartments(): BaseResponse<DepartmentResponse>
    suspend fun getAllDBDepartments():List<DepartmentEntity>
    suspend fun saveDBDepartment(departmentEntity: DepartmentEntity):Long
}