package com.nutrition.balanceme.data.remote

import com.nutrition.balanceme.data.models.Auth
import com.nutrition.balanceme.data.models.Credentials
import com.nutrition.balanceme.util.BASE_URL
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class AuthService @Inject constructor(private val client: HttpClient) {

    suspend fun login(credentials: Credentials): Auth {
        val url = "$BASE_URL/api/auth/login"
        return client.post { url(url); body = credentials }
    }

    suspend fun signup(credentials: Credentials): Auth {
        val url = "$BASE_URL/api/auth/signup"
        return client.post{ url(url); body = credentials }
    }

    suspend fun forgot(email: String): Any {
        val url = "$BASE_URL/api/auth/forgot/$email"
        return client.get(url)
    }
}