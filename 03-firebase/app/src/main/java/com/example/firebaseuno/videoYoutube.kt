package com.example.firebaseuno

//import androidx.appcompat.app.AppCompatActivity

import android.app.ActionBar
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView


class videoYoutube : YouTubeBaseActivity() {
    val  VIDEO_ID = "JUgI4j1fydk"
    val YOUTUBE_API_KEY = "AIzaSyAzGyZmTa4dkzmu3sI2AEUDvkoFSVW20_c"
    private var fullscreen = false
    private  lateinit var youtubePlayer: YouTubePlayerView
    private  lateinit var btnPlay: Button
    lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_youtube)

        youtubePlayer = findViewById(R.id.youtubePlayer)
        btnPlay = findViewById(R.id.btn_play)
        
        youtubePlayerInit = object :YouTubePlayer.OnFullscreenListener,
            YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                //p1?.setFullscreen(true)
                p1?.loadVideo(VIDEO_ID)
                Log.i("yt","sirvio")
                //p1?.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)
                //p1?.play()
                //p1?.setRotation(90.toFloat())

                p1?.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {

                Log.i("yt","no sirvio")
                Toast.makeText(applicationContext,"Failed",Toast.LENGTH_SHORT).show()
            }

            override fun onFullscreen(p0: Boolean) {
                fullscreen = p0;
                //getAct
            }

        }

        btnPlay.setOnClickListener {
            youtubePlayer.initialize(YOUTUBE_API_KEY,youtubePlayerInit)
        }



    }


}