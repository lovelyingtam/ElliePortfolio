package com.ellie.jetportfolio.ui._sample

import com.ellie.jetportfolio.data.api.model.UUIDModel

data class SampleUiState(
    val count: Int = 0,
    var uuidModel: UUIDModel? = null,
    val isRefreshing: Boolean = false,
)
