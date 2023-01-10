package com.ellie.jetportfolio.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType

abstract class JsonNavType<T> : NavType<T>(isNullableAllowed = false) {
    abstract fun fromJsonParse(value: String): T
    abstract fun T.getJsonParse(): String

    override fun parseValue(value: String): T = fromJsonParse(value)

    override fun get(bundle: Bundle, key: String): T? =
        bundle.getString(key)?.let { parseValue(it) }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, value.getJsonParse())
    }
}

//// Sample:
//class BusinessCardArgType : JsonNavType<BusinessCard>() {
//    override fun fromJsonParse(value: String): BusinessCard = value.fromJsonToBusinessCard()
//
//    override fun BusinessCard.getJsonParse(): String = toJson()
//}