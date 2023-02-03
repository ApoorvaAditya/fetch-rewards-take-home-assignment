package com.example.fetchrewardstakehome.utilities

sealed class AsyncStatus<out T> {
    data class Success<out R>(val value: R) : AsyncStatus<R>()
    data class Failure(val message: String): AsyncStatus<Nothing>()
    data class Loading(val loading: Boolean = true) : AsyncStatus<Nothing>()
}
