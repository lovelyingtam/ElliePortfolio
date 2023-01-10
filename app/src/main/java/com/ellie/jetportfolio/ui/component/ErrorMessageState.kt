package com.ellie.jetportfolio.ui.component

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ellie.jetportfolio.R

data class ErrorMessageState(
    val exception: Exception? = null,
    @StringRes val errorTitleId: Int? = null,
    @StringRes val errorMessageId: Int? = null,
    val errorTitle: String? = null,
    val errorMessage: String? = null,
) {

    @Composable
    fun getTitle(): String? {
        return exception?.let {
            stringResource(id = R.string.error)
        } ?: errorTitleId?.let {
            stringResource(id = errorTitleId)
        } ?: errorTitle
    }

    @Composable
    fun getMessage(): String? {
        return exception?.let {
            when (exception) {
                is retrofit2.HttpException -> {
                    when (exception.message?.trim()?.uppercase()) {
                        "HTTP 404" -> stringResource(id = R.string.err_http_404_msg)
                        "HTTP 503" -> stringResource(id = R.string.err_http_503_msg)
                        else -> stringResource(id = R.string.err_http_unknown_msg)
                    }
                }
                else -> {
                    // No network - java.net.UnknownHostException: Unable to resolve host "raw.githubusercontent.com": No address associated with hostname
                    if (exception.message?.startsWith("Unable to resolve host") == true) {
                        stringResource(id = R.string.err_no_network_msg)
                    } else {
                        stringResource(id = R.string.err_default_msg)
                    }
                }
            }
        } ?: errorMessageId?.let {
            stringResource(id = errorMessageId)
        } ?: errorMessage
    }
}