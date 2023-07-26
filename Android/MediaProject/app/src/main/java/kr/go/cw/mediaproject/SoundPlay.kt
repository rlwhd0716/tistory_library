package kr.go.cw.mediaproject

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import java.io.IOException

object SoundPlay {
    val TAG = javaClass.simpleName

    private fun soundAlarm(context: Context?): AssetFileDescriptor? =
        context?.resources?.openRawResourceFd(R.raw.coin)

    private fun soundYes(context: Context?): AssetFileDescriptor? =
        context?.resources?.openRawResourceFd(R.raw.correct)

    val SOUND_ALARM = 0
    val SOUND_YES = 1

    fun soundType(type: Int, context: Context?): AssetFileDescriptor? =
        when (type) {
            SOUND_ALARM -> soundAlarm(context)
            SOUND_YES -> soundYes(context)
            else -> soundAlarm(context)
        }

    fun soundStart(type: Int, context: Context?): MediaPlayer {
        val descriptor = soundType(type, context)
        val attr = AudioAttributes.Builder().apply {
            setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        }.build()

        val mediaPlayer = MediaPlayer()

        descriptor?.let {
            try {
                mediaPlayer.apply {
                    setDataSource(it.fileDescriptor, it.startOffset, it.length)
                    setAudioAttributes(attr)
                    prepare()
                    start()
                }
            } catch (e: IOException) {
                soundStop(mediaPlayer)
                Log.e(TAG,"IOException - ${e.message}")
            } finally {
                try {
                    it.close()
                } catch (e: IOException) {
                    Log.e(TAG,"IOException - ${e.message}")
                }
            }
        }

        return mediaPlayer
    }

    fun soundStop(mediaPlayer: MediaPlayer?) {
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }

    fun soundYesPlay(context: Context?) {
        soundStart(SOUND_ALARM, context).setOnCompletionListener { mp1 ->
            mp1.release()
            soundStart(SOUND_YES, context).setOnCompletionListener { mp2 ->
                mp2.release()
            }
        }
    }
}