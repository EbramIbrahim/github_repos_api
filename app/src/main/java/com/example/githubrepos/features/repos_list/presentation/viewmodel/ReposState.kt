package com.example.githubrepos.features.repos_list.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.paging.PagingData
import com.example.githubrepos.features.repos_list.data.model.GithubUserDtoItem

data class ReposState(
    val error: String? = null,
)
