package com.nutrition.balanceme.data.models

import com.nutrition.balanceme.domain.models.Profile
import kotlinx.serialization.Serializable

@Serializable
data class Auth(val message: String, val token: String = "", val user: Profile)