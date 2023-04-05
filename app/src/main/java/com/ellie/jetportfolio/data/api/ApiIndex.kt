package com.ellie.jetportfolio.data.api

import com.ellie.jetportfolio.data.api.model.Profile
import com.ellie.jetportfolio.data.api.model.UUIDModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiIndex {

    // https://github.com/lovelyingtam/lovelyingtam.github.io/blob/master/demo/api/profile_ellietam_v1.json
    @GET(ApiConstants.END_POINT_GET_PROFILE)
    suspend fun getProfile(@Path("profile_id") profileId: String): Profile

    @GET("https://httpbin.org/uuid")
    suspend fun getUUID(): UUIDModel
}