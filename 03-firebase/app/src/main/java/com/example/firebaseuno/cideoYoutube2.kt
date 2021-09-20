package com.example.firebaseuno

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class cideoYoutube2 : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    val  VIDEO_ID = "xY1eqVSSuus"
    val YOUTUBE_API_KEY = "AIzaSyAzGyZmTa4dkzmu3sI2AEUDvkoFSVW20_c"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cideo_youtube2)

        val youTubeView = findViewById(R.id.youtube_view) as YouTubePlayerView

        youTubeView.initialize(YOUTUBE_API_KEY, this)
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if (!p2) {

            //p1?.cueVideo(VIDEO_ID)
            //p1?.loadVideo(VIDEO_ID)
            p1?.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE)
            p1?.loadVideo(VIDEO_ID)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {

    }


}