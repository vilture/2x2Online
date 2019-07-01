package vilture.tk.a2x2online

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ExoPlayer.EventListener
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.exoplayer2.audio.AudioRendererEventListener
import com.google.android.exoplayer2.video.VideoRendererEventListener
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.ExoPlayer



class MainActivity : AppCompatActivity() {

    private val handler = Handler()
    private var exoPlayer: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContentView(R.layout.activity_main)

        play()

        retry.setOnClickListener {
            retry()
        }
    }

    private fun play() {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(this), DefaultTrackSelector(), DefaultLoadControl()
        )

        val playerView = findViewById<SimpleExoPlayerView>(R.id.exo_player_view)
        val uri = Uri.parse(BuildConfig.STREAMING_URL)

        playerView.player = exoPlayer

        val dataSourceFactory = DefaultDataSourceFactory(this, "user-agent")
        val mediaSource = HlsMediaSource(uri, dataSourceFactory, handler, null)

        exoPlayer?.prepare(mediaSource)
        exoPlayer?.playWhenReady = true
    }


    private fun stop() {
        exoPlayer?.playWhenReady = false
        exoPlayer?.release()
    }

    private fun retry() {
        stop()
        play()
        retry.visibility = View.INVISIBLE
    }

    override fun onBackPressed() {
        finish()
    }


//    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//        when (playbackState) {
//
//            ExoPlayer.STATE_IDLE -> {
//                retry.visibility = View.VISIBLE
//            }
//            else -> {
//            }
//        }
//    }
}




