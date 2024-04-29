package com.ramzmania.aicammvd.utils

import android.content.Context
import android.media.MediaPlayer
/**
 * Utility class for managing MediaPlayer instances.
 *
 * @param context The context used to create MediaPlayer instances.
 */
class MediaPlayerUtil(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    /**
     * Plays the sound specified by the given resource ID.
     *
     * @param resourceId The resource ID of the sound to be played.
     */
    fun playSound(resourceId: Int) {
        stopSound() // Stop any currently playing sound before playing a new one
        mediaPlayer = MediaPlayer.create(context, resourceId)
        mediaPlayer?.start()
    }
    /**
     * Checks if a sound is currently being played.
     *
     * @return `true` if a sound is currently being played, `false` otherwise.
     */
    fun isPlayingSound():Boolean
    {
        return if(mediaPlayer!=null) {
            mediaPlayer!!.isPlaying
        }else {
            false
        }
    }

    /**
     * Stops the currently playing sound.
     */
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
