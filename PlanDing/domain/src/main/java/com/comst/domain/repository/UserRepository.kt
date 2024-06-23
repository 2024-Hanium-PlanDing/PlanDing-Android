package com.comst.domain.repository

import com.comst.domain.model.user.UserProfile
import com.comst.domain.util.ApiResult

interface UserRepository {
    suspend fun getUserProfile(): ApiResult<UserProfile>
}