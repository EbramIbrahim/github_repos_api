package com.example.githubrepos.features.repos_list.presentation.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubrepos.features.repos_list.data.model.GithubUserDtoItem


@Composable
fun ReposItem(
    modifier: Modifier = Modifier,
    gitHubItem: GithubUserDtoItem,
    context: Context
) {

    Row(
        modifier = modifier.padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = context).data(gitHubItem.owner.avatarUrl)
                .build(),
            contentDescription = "Owner Photo",
            modifier = Modifier
                .clip(RoundedCornerShape(22.dp))
                .size(50.dp).align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop
        )


        Column(
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Text(
                text = gitHubItem.owner.login,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = gitHubItem.name,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
            )


        }


    }


}









