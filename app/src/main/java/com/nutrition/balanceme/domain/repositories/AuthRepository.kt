package com.nutrition.balanceme.domain.repositories

import com.nutrition.balanceme.data.models.Credentials

interface AuthRepository {

    suspend fun login(credentials: Credentials): String

    suspend fun signup(credentials: Credentials): String

    suspend fun forgot(email: String): String
}
