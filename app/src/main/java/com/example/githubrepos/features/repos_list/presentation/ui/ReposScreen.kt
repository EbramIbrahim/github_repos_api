package com.example.githubrepos.features.repos_list.presentation.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.githubrepos.features.repos_list.presentation.viewmodel.ReposViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReposScreen(
    modifier: Modifier = Modifier,
    viewModel: ReposViewModel = hiltViewModel(),
    context: Context,
    navController: NavController
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            title = {
                Text(
                    text = "Github Repositories",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            actions = {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                    contentDescription = "Logout",
                    modifier = Modifier
                        .clickable {
                            viewModel.logout()
                            navController.navigateUp()
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )
        SearchBarField(
            modifier = Modifier
                .padding(end = 8.dp),
            onQueryChanged = {
                viewModel.updateSearchQuery(it)
            },
            onSearch = {
                viewModel.resetPagination()
                viewModel.loadRepos(searchQuery)
            },
            viewModel = viewModel,
            context = context
        )


        Spacer(modifier = Modifier.height(8.dp))

        ReposList(context = context, viewModel = viewModel)


    }
}











