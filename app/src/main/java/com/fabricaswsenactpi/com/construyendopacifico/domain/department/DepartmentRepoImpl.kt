package com.fabricaswsenactpi.com.construyendopacifico.domain.department

import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.department.DepartmentDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.DepartmentEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.DepartmentResponse

class DepartmentRepoImpl(private val departmentDataSource: DepartmentDataSource):DepartmentRepo {
    override suspend fun getDepartments(): BaseResponse<DepartmentResponse> = departmentDataSource.getDepartments()
    override suspend fun getAllDBDepartments(): List<DepartmentEntity> = departmentDataSource.getAllDepartments()
    override suspend fun saveDBDepartment(departmentEntity: DepartmentEntity): Long = departmentDataSource.saveDepartment(departmentEntity)
}