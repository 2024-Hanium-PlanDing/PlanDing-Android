package com.comst.data.repository

import com.comst.data.model.user.toDomainModel
import com.comst.data.retrofit.ApiHandler
import com.comst.data.retrofit.UserService
import com.comst.domain.model.user.UserProfile
import com.comst.domain.repository.UserRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val apiHandler: ApiHandler
): UserRepository {

    override suspend fun getUserProfile(): ApiResult<UserProfile> {
        return apiHandler.handle(
            execute = { userService.getUserProfile() },
            mapper = { response -> response.toDomainModel()}
        )
    }
}