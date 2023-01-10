package com.ellie.jetportfolio.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ellie.jetportfolio.data.model.BusinessCard
import com.ellie.jetportfolio.data.repository.ProfileRepo
import com.ellie.jetportfolio.ui.component.ErrorMessageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepo: ProfileRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState>
        get() = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _uiState.value = ProfileUiState()

        viewModelScope.launch {
            try {
                _uiState.value.profile = profileRepo.getProfile(profileId = "profile_ellietam_v1")
            } catch (ex: Exception) {
                _uiState.value.isShowFullScreenErrorMessage = true
                _uiState.value.errorMessageState = ErrorMessageState(exception = ex)
                Timber.e(ex, "refresh() - error")
            }
            _uiState.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }

    fun generateBusinessCard(): BusinessCard {
        val profile = _uiState.value.profile!!
        return BusinessCard(
            pictureUrl = profile.pictureUrl,
            nickname = profile.nickname,
            fullName = profile.fullName,
            position = profile.displayPosition,
            phoneNumber = profile.phoneNumber,
            email = profile.email,
            socialMedia = profile.socialMedia,
        )
    }
}