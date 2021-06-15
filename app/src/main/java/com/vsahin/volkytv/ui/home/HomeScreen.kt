package com.vsahin.volkytv.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vsahin.volkytv.R
import com.vsahin.volkytv.data.model.Entry
import com.vsahin.volkytv.ui.common.view.EntryPoster
import com.vsahin.volkytv.ui.common.view.ErrorView
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToDetail: (String) -> Unit,
    navigateToPlayer: (String) -> Unit
) {
    val state by viewModel.homeState

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            HomeContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                entries = state.contents.entries,
                isRefreshing = state.isLoading,
                onRefresh = { viewModel.getContents() },
                navigateToDetail = { entryId ->
                    navigateToDetail(entryId)
                },
                navigateToPlayer = { entryId ->
                    navigateToPlayer(entryId)
                }
            )
        }
    )

    state.error?.let {
        if (state.contents.entries.isEmpty()) {
            ErrorView(error = it.localizedMessage ?: "") {
                viewModel.getContents()
            }
        } else {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(it.localizedMessage ?: "error")
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    entries: List<Entry>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    navigateToDetail: (String) -> Unit,
    navigateToPlayer: (String) -> Unit,
) {
    val isRefresh = rememberSwipeRefreshState(isRefreshing)

    SwipeRefresh(
        state = isRefresh,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            modifier = modifier
        ) {
            if (entries.isNotEmpty()) {
                item(key = "header") {
                    HeaderRowList(
                        entries = entries.subList(0, 5),
                        onClick = navigateToPlayer
                    )
                }

                item {
                    Text(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 8.dp,
                        ), text = stringResource(id = R.string.popular_now)
                    )
                }

                item(key = "topRow") {
                    TopRow(
                        entries = entries.subList(5, 10),
                        onClick = navigateToDetail
                    )
                }

                item {
                    Text(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 8.dp,
                        ), text = stringResource(id = R.string.for_you)
                    )
                }

                item(key = "forYou") {
                    EntryPoster(
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        entry = entries[10],
                        onClick = navigateToDetail
                    )
                }

                item {
                    Text(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 8.dp,
                        ), text = stringResource(id = R.string.explore)
                    )
                }

                items(
                    entries.subList(11, entries.size - 1),
                    key = {
                        it.id
                    }
                ) { entry ->
                    EntryPoster(
                        entry = entry,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.77f),
                        onClick = navigateToDetail
                    )
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun HeaderRowList(
    entries: List<Entry>,
    onClick: (String) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = entries.size)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
    ) {
        HorizontalPager(state = pagerState) { page ->
            HeaderListItem(
                entry = entries[page],
                onClick = onClick
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
        )
    }
}

@Composable
fun TopRow(
    entries: List<Entry>,
    onClick: (String) -> Unit,
) {
    LazyRow {
        items(
            entries,
            key = {
                it.id
            }
        ) { entry ->
            EntryPoster(
                entry = entry,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .width(150.dp)
                    .aspectRatio(0.56f),
                onClick = onClick
            )
        }
    }
}