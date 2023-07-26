package kr.go.cw.mediaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button1).setOnClickListener {
            SoundPlay.soundStart(SoundPlay.SOUND_ALARM, this@MainActivity)
                .setOnCompletionListener {
                    it.release()
                }
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            SoundPlay.soundYesPlay(this@MainActivity)
        }
    }
}