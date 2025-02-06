package com.dicoding.flavorquest.ui.presentation.detail.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
@Composable
fun YoutubePlay(videoId: String) {
    val context = LocalContext.current
    var youTubePlayerView: YouTubePlayerView? = null

    AndroidView(factory = {
        YouTubePlayerView(it).apply {
            youTubePlayerView = this
            addYouTubePlayerListener(
                object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(videoId, 0f)
                    }
                }
            )
        }
    })

    DisposableEffect(Unit) {
        onDispose {
            youTubePlayerView?.release()
        }
    }
}
