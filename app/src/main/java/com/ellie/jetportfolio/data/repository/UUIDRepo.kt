package com.ellie.jetportfolio.data.repository

import com.ellie.jetportfolio.data.api.ApiIndex
import com.ellie.jetportfolio.data.api.model.Profile
import com.ellie.jetportfolio.data.api.model.UUIDModel
import javax.inject.Inject

class UUIDRepo @Inject constructor(
    private val apiIndex: ApiIndex
) {
    suspend fun getUUID(): UUIDModel {
        return apiIndex.getUUID()
    }
}