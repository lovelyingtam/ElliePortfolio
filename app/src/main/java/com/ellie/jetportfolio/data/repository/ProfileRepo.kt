package com.ellie.jetportfolio.data.repository

import com.ellie.jetportfolio.data.api.ApiIndex
import com.ellie.jetportfolio.data.api.model.Profile
import javax.inject.Inject

class ProfileRepo @Inject constructor(
    private val apiIndex: ApiIndex
) {
    suspend fun getProfile(profileId: String): Profile {
        return apiIndex.getProfile(profileId = profileId)
    }
}