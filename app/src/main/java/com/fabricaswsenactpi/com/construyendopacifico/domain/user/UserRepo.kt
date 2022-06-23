package com.fabricaswsenactpi.com.construyendopacifico.domain.user

import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.UsersEntity

interface UserRepo {
    suspend fun getAllUsersByEmail(userNick:String):List<UsersEntity>
    suspend fun saveUser(userEntity: UsersEntity):Long

}