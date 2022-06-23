package com.fabricaswsenactpi.com.construyendopacifico.data.models.local.user

import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.UsersEntity

class UserDataSource(private val dao : UserDao) {
    suspend fun getAllUsersByEmail(userNick:String):List<UsersEntity> = dao.getAllUsersByEmail(userNick)
    suspend fun saveUser(userEntity: UsersEntity):Long = dao.saveUser(userEntity)
}