package com.nutrition.balanceme.domain.usecases

import com.nutrition.balanceme.data.models.Credentials
import com.nutrition.balanceme.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthUseCases @Inject constructor(private val authRepository: AuthRepository) {

    suspend fun forgot(email: String): String {
        return authRepository.forgot(email)
    }

    suspend fun login(credentials: Credentials): String {
        return authRepository.login(credentials)
    }

    suspend fun signup(credentials: Credentials): String {
        return authRepository.signup(credentials)
    }
}