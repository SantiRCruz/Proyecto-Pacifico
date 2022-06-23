package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.department

import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.WebService
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.DepartmentEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.BaseResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.DepartmentResponse
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.MunicipalitiesResponse

class DepartmentDataSource (private val webService: WebService, private val dao : DepartmentDao){
    suspend fun getDepartments():BaseResponse<DepartmentResponse> = webService.getDepartments()
    suspend fun getAllDepartments():List<DepartmentEntity> = dao.getAllDepartments()
    suspend fun saveDepartment(departmentEntity: DepartmentEntity):Long = dao.saveDepartment(departmentEntity)


}