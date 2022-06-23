package com.fabricaswsenactpi.com.construyendopacifico.domain.user

import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.user.UserDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.UsersEntity
import com.fabricaswsenactpi.com.construyendopacifico.domain.user.UserRepo

class UserRepoImpl (private val userDataSource: UserDataSource): UserRepo {
    override suspend fun getAllUsersByEmail(userNick: String): List<UsersEntity> = userDataSource.getAllUsersByEmail(userNick)
    override suspend fun saveUser(userEntity: UsersEntity):Long = userDataSource.saveUser(userEntity)
}