package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.UsersEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM usersentity WHERE user_nick = :userNick")
    suspend fun getAllUsersByEmail(userNick:String):List<UsersEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveUser(userEntity: UsersEntity):Long
}