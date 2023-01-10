package com.ellie.jetportfolio.data.model

import android.net.Uri
import com.ellie.jetportfolio.data.api.model.Profile
import com.ellie.jetportfolio.utils.jsonToObject
import com.ellie.jetportfolio.utils.objectToJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi


@JsonClass(generateAdapter = true)
data class BusinessCard(
    @Json val pictureUrl: String?,
    @Json val nickname: String,
    @Json val fullName: String,
    @Json val position: String,
    @Json val phoneNumber: String,
    @Json val email: String,
    @Json val socialMedia: List<Profile.SocialMedia>?,
) {
    override fun toString(): String = Uri.encode(toJson())

    fun toJson(): String {
        val moshi = Moshi.Builder().build()
        return moshi.objectToJson(this)
    }
}

fun String.fromJsonToBusinessCard(): BusinessCard {
    val moshi = Moshi.Builder().build()
    return moshi.jsonToObject<BusinessCard>(this)!!
}