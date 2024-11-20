package com.example.githubrepos.common.utils

import java.lang.Exception

sealed class Resource <out Result> {
    data class Success<out Result>(val result: Result): Resource<Result>()
    data class Failure(val exception: Exception): Resource<Nothing>()
    data object Loading: Resource<Nothing>()
}