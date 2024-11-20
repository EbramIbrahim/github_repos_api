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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githubrepos.features.repos_list.presentation.viewmodel.ReposViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarField(
    modifier: Modifier = Modifier,
    onQueryChanged:(String) -> Unit,
    onSearch:(String) -> Unit,
    viewModel: ReposViewModel,
    context: Context
) {

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val reposState by viewModel.reposState.collectAsStateWithLifecycle()

    var isActive by rememberSaveable {
        mutableStateOf(false)
    }

    val listState = rememberLazyListState()



    SearchBar(
        modifier = modifier,
        inputField = {
            SearchBarDefaults.InputField(
                query = searchQuery,
                onQueryChange = {
                    onQueryChanged(it)
                },
                onSearch = onSearch,
                expanded = isActive,
                onExpandedChange = {
                    isActive = it
                    viewModel.resetPagination()
                },
            )
        },
        expanded = isActive,
        onExpandedChange = {
            isActive = it
        },
        colors = SearchBarDefaults.colors(),
    ) {
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


}












