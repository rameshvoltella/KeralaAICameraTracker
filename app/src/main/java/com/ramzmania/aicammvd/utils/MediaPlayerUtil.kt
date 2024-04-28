package com.ramzmania.aicammvd.utils

import android.content.Context
import android.media.MediaPlayer

class MediaPlayerUtil(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun playSound(resourceId: Int) {
        stopSound() // Stop any currently playing sound before playing a new one
        mediaPlayer = MediaPlayer.create(context, resourceId)
        mediaPlayer?.start()
    }

    fun stopSound() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
    }
}
