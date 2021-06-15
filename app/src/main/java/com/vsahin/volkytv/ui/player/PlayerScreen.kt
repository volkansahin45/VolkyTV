package com.vsahin.volkytv.ui.player

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel,
    entryId: String
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val state by viewModel.playerState

    LaunchedEffect(key1 = entryId, block = {
        viewModel.getDetail(entryId)
    })

    state.entry?.let {
        val player = remember {
            SimpleExoPlayer.Builder(context).build().apply {
                val mediaItem: MediaItem =
                    MediaItem.fromUri(state.entry!!.contents[0].url)
                setMediaItem(mediaItem)
                prepare()
                seekTo(state.playerPosition)
                play()
            }
        }

        val playerView = remember {
            val playerView = PlayerView(context)
            lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_START)
                fun onStart() {
                    playerView.onResume()
                    player.play()
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
                fun onStop() {
                    viewModel.updatePlayerPosition(player.currentPosition)
                    playerView.onPause()
                    player.pause()
                }
            })
            playerView.player = player
            playerView
        }

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            factory = { playerView }
        )

        DisposableEffect(key1 = player) {
            onDispose {
                player.release()
            }
        }
    }
}