package com.mastercoding.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private var milli: Int = 0
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.timerText)
    }

    fun onClickStart(view: View) {
        if (!isRunning) {
            startTimer()
            isRunning = true
        }
    }

    fun onClickStop(view: View) {
        stopTimer()
        isRunning = false
    }

    fun onClickReset(view: View) {
        stopTimer()
        milli = 0
        updateTimerText()
        isRunning = false
    }

    private fun startTimer() {
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                milli += 10
                updateTimerText()
                handler.postDelayed(this, 10)
            }
        }
        handler.post(runnable)
    }

    private fun stopTimer() {
        handler.removeCallbacks(runnable)
    }

    private fun updateTimerText() {
        val ms = milli % 1000
        val s = (milli / 1000) % 60
        val m = (milli / 1000) / 60

        val formatString = String.format(Locale.getDefault(), "%02d:%02d:%03d", m, s, ms)
        textView.text = formatString
    }
}
