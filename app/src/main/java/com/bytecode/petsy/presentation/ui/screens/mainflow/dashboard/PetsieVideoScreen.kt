package com.bytecode.petsy.presentation.ui.screens.mainflow.dashboard

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bytecode.petsy.presentation.ui.commonui.PetsyImageBackground
import com.bytecode.petsy.presentation.ui.commonui.headers.HeaderOnboarding
import com.bytecode.petsy.presentation.ui.screens.mainflow.MainFlowViewModel
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun PetsieVideoScreen(
    viewModel: MainFlowViewModel,
    navController: NavHostController = rememberNavController()
) {

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            PetsyImageBackground()
            HeaderOnboarding()
            VideoView()
        }
    }
}

@Composable
fun VideoView() {
    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(LocalContext.current)
        .build()
        .also { exoPlayer ->
            val mediaItem = MediaItem.Builder()
                .setUri(Uri.parse("asset:///petsie_video.mp4"))
                .build()
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
        }

    DisposableEffect(
        AndroidView(modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)),
            factory = {
                StyledPlayerView(context).apply {
                    this.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                    this.useController = true
                    player = exoPlayer
                }
            })
    ) {
        onDispose { exoPlayer.release() }
    }
}