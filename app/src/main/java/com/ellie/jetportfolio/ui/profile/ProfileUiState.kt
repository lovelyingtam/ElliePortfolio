package com.ellie.jetportfolio.ui.profile

import com.ellie.jetportfolio.data.api.model.Profile
import com.ellie.jetportfolio.ui.component.ErrorMessageState

data class ProfileUiState(
    val isRefreshing: Boolean = true,
    var profile: Profile? = null,
    var isShowFullScreenErrorMessage: Boolean = false,
    var errorMessageState: ErrorMessageState? = null,
)