package com.vsahin.volkytv.ui.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vsahin.volkytv.data.model.Category
import com.vsahin.volkytv.data.model.Entry
import com.vsahin.volkytv.ui.common.toReadableDate
import com.vsahin.volkytv.ui.common.view.Credits
import com.vsahin.volkytv.ui.common.view.ErrorView
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    entryId: String,
    navigateToPlayer: (String) -> Unit
) {
    val state by viewModel.detailState

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            DetailContent(
                entry = state.entry,
                isLoading = state.isLoading,
                onRefresh = { viewModel.getDetail(entryId = entryId) },
                onPlayClicked = { navigateToPlayer(entryId) }
            )
        }
    )

    state.error?.let {
        if (state.entry == null) {
            ErrorView(error = it.localizedMessage ?: "") {
                viewModel.getDetail(entryId = entryId)
            }
        } else {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(it.localizedMessage ?: "error")
            }
        }
    }

    LaunchedEffect(key1 = entryId, block = {
        viewModel.getDetail(entryId)
    })
}

@Composable
private fun DetailContent(
    entry: Entry?,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onPlayClicked: () -> Unit,
) {
    val isRefresh = rememberSwipeRefreshState(isRefreshing = isLoading)

    SwipeRefresh(
        state = isRefresh,
        onRefresh = onRefresh
    ) {
        entry?.let {
            val yearDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
            val publishedYear = yearDateFormat.format(Date(entry.publishedDate))
            val duration = entry.contents[0].duration.toReadableDate()
            val pegi = entry.parentalRatings[0].rating
            val metadata = "$publishedYear | $pegi | $duration"

            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Header(entry = it, onPlayClicked = onPlayClicked)
                Column(modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)) {
                    Text(text = metadata)
                    Text(text = entry.description, fontWeight = FontWeight.Bold)
                }
                Credits(
                    modifier = Modifier.padding(top = 16.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    credits = entry.credits
                )
            }
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    entry: Entry,
    onPlayClicked: () -> Unit
) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
            painter = rememberCoilPainter(
                request = entry.images[0].url,
                fadeIn = true,
                shouldRefetchOnSizeChange = { _, _ -> false },
            ),
            contentScale = ContentScale.Crop,
            contentDescription = "My content description",
        )

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
                .background(color = Color(0xAA000000), shape = RoundedCornerShape(8.dp))
                .padding(8.dp),
            text = entry.title,
            style = MaterialTheme.typography.h5,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Icon(
            imageVector = Icons.Filled.PlayArrow,
            tint = Color.White,
            contentDescription = "share",
            modifier = Modifier
                .align(Center)
                .size(100.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(color = Color(0xAA000000), shape = RoundedCornerShape(8.dp))
                .clickable { onPlayClicked() }
        )

        Categories(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            categories = entry.categories
        )
    }
}

@Composable
fun Categories(
    modifier: Modifier = Modifier,
    categories: List<Category>
) {
    LazyRow(modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories) { category ->
            Text(
                text = category.title,
                modifier = Modifier
                    .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(4.dp)
            )
        }
    }
}