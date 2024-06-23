package com.comst.data.repository

import android.content.Context
import com.comst.data.model.user.toDomainModel
import com.comst.data.retrofit.UserService
import com.comst.data.retrofit.apiHandler
import com.comst.domain.model.user.UserProfile
import com.comst.domain.repository.UserRepository
import com.comst.domain.util.ApiResult
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val context: Context
): UserRepository {

    override suspend fun getUserProfile(): ApiResult<UserProfile> {
        return apiHandler(
            context = context,
            execute = { userService.getUserProfile() },
            mapper = { response -> response.toDomainModel()}
        )
    }
}