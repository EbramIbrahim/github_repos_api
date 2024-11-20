package com.example.githubrepos.common.presentation.navigation

interface Screen {

    @kotlinx.serialization.Serializable
    data object SignupScreen: Screen

    @kotlinx.serialization.Serializable
    data object LoginScreen: Screen

    @kotlinx.serialization.Serializable
    data object ReposListScreen: Screen




}