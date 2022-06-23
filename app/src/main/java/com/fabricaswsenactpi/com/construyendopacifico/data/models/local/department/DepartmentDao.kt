package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.department

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.Query
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.DepartmentEntity
import retrofit2.http.GET

@Dao
interface DepartmentDao {

    @Query("SELECT * FROM departmententity")
    suspend fun getAllDepartments():List<DepartmentEntity>

    @Insert(onConflict = ABORT)
    suspend fun saveDepartment(departmentEntity: DepartmentEntity):Long

}