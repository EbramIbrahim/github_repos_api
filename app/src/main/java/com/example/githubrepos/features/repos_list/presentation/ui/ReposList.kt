package com.example.githubrepos.features.repos_list.presentation.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githubrepos.features.repos_list.presentation.viewmodel.ReposViewModel

@Composable
fun ReposList(
    modifier: Modifier = Modifier,
    viewModel: ReposViewModel,
    context: Context,
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val reposState by viewModel.reposState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.resetPagination()
        viewModel.loadRepos(searchQuery)
    }

    val listState = rememberLazyListState()


    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(viewModel.repos) { repos ->
            ReposItem(gitHubItem = repos, context = context)
        }
        item {
            if (reposState.error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = reposState.error ?: "", fontSize = 30.sp, color = Color.Red)
                }
            }
            if (viewModel.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }
        }

    }


    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == viewModel.repos.lastIndex && !viewModel.isLoading && !viewModel.isLastPage) {
                    viewModel.loadRepos(searchQuery)
                }
            }
    }


}