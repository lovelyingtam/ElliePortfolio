package com.ellie.jetportfolio.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UUIDModel(
    @Json(name = "uuid") val uuid: String?
)
