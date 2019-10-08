package com.lambdaschool.mediaservices

import android.graphics.drawable.Animatable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_media.*

class MediaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        // Disable the play/pause button by default
        btn_play_pause.isEnabled = false

        // Add the play/pause functionality
        playOrPauseFunctionality()

        // Add functionality to listen to seekbar
        seekBarFunctionality()
    }

    private fun playOrPauseFunctionality() {
        btn_play_pause.setOnClickListener {
            if (video_view.isPlaying) {
                video_view.pause()
                btn_play_pause.setImageDrawable(getDrawable(R.drawable.avd_anim_pause_play))
            } else {
                video_view.start()
                btn_play_pause.setImageDrawable(getDrawable(R.drawable.avd_anim_play_pause))
            }

            val drawable = btn_play_pause.drawable
            if (drawable is Animatable) {
                (drawable as Animatable).start()
            }
        }
    }

    private fun seekBarFunctionality() {
        video_seek_bar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                seekBar.let {
                    video_view.seekTo(seekBar!!.progress)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        // Set the video URI to the videoview
        video_view.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.jellyfish))

        // Set an onPreparedListener to the videoview and enable the play/pause button
        video_view.setOnPreparedListener { mp ->
            btn_play_pause.isEnabled = true
            mp?.let {
                video_seek_bar.max = mp.duration
            }
        }

        val handler = Handler()
        this@MediaActivity.runOnUiThread(object: Runnable {
            override fun run() {
                if (video_view != null) {
                    val currentPosition = video_view.currentPosition
                    video_seek_bar.progress = currentPosition
                    
                    val duration = video_view.duration/1000
                    val seconds = video_view.currentPosition/1000
                    val currentSeconds = String.format("%02d", seconds)
                    val durationSeconds = String.format("%02d", duration-seconds)
                    seekbar_current_time.text = "00:" + currentSeconds
                    seekbar_remaining_time.text = "00:" + durationSeconds
                }

                handler.postDelayed(this, 1000)
            }
        })
    }

    override fun onStop() {
        super.onStop()
        // Pause the video when onStop is called
        video_view.pause()
    }
}
