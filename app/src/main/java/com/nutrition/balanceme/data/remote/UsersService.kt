package com.nutrition.balanceme.data.remote

import com.nutrition.balanceme.domain.models.Profile
import com.nutrition.balanceme.util.BASE_URL
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class UsersService @Inject constructor(val client: HttpClient) {

    suspend fun getProfile(id: String): Profile {
        val url = "$BASE_URL/api/users/profile/$id"
        return client.get(url)
    }

    suspend fun getChefs(): List<Profile> {
        val url = "$BASE_URL/api/users/discover"
        return client.get(url)
    }

    suspend fun updateProfile(update: Profile): Profile {
        val url = "$BASE_URL/api/users/update"
        return client.put { url(url); body = update }
    }
}