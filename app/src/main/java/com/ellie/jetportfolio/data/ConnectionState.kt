package com.ellie.jetportfolio.data

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}