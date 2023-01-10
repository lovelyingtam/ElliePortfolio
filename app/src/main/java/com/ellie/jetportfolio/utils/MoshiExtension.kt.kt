package com.ellie.jetportfolio.utils

import com.squareup.moshi.Moshi

/*
* [Moshi] extension to transform an object to json
* */
inline fun <reified T> Moshi.objectToJson(data: T): String = adapter(T::class.java).toJson(data)

/*
* [Moshi] extension to transform json to an object
* */
inline fun <reified T> Moshi.jsonToObject(json: String): T? = adapter(T::class.java).fromJson(json)