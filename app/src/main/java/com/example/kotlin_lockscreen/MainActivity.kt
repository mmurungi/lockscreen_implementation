package com.example.kotlin_lockscreen

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.kotlin_lockscreen.ui.theme.Kotlin_lockscreenTheme
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Kotlin_lockscreenTheme{
                Surface(color = Color.Blue
                ) {
                    VideoPlayer(Modifier.fillMaxHeight())
                }

            }
        }
    }
}


@Composable
 fun VideoPlayer(modifier: Modifier){
     val videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    val context = LocalContext.current
    val currentView = LocalView.current
    val player = SimpleExoPlayer.Builder(context).build()
    val playerView = PlayerView(context)
    val mediaItem = MediaItem.fromUri(videoUrl)

    val playWhenReady by rememberSaveable {
        mutableStateOf(true)
    }
    player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    player.setMediaItem(mediaItem)

    playerView.player = player

    player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    player.repeatMode = Player.REPEAT_MODE_ONE
    LaunchedEffect(player){

        player.prepare()
        player.playWhenReady = playWhenReady
    }



    DisposableEffect(Unit){


        playerView.apply {


            keepScreenOn = true
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT )
        }

        onDispose {
            currentView.keepScreenOn = false
            player.release()
        }
    }



    AndroidView(factory = {
playerView

    })
 }




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Kotlin_lockscreenTheme {
//VideoPlayer()
    }
}