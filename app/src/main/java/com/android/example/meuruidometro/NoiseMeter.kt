package com.android.example.meuruidometro

import android.media.MediaRecorder
import android.util.Log
import java.io.File
import java.io.IOException
import kotlin.math.log10

class NoiseMeter(outputPath: String) : MediaRecorder() {

    private val LOG_TAG = "TEST"
    private var output: String = outputPath
    private var recording: Boolean = false

    fun startRecording() {
        if (recording) return

        this.apply {
            setAudioSource(AudioSource.MIC)
            setOutputFormat(OutputFormat.THREE_GPP)
            setOutputFile(output)
            setAudioEncoder(AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(LOG_TAG, e.message)
            }

            start()
        }

        recording = true
    }

    fun stopRecording() {
        if (!recording) return

        this.apply {
            stop()
            reset()
            release()
        }

        recording = false
    }

    override fun getMaxAmplitude(): Int {
        // TODO: Find equation to define DB
        val dbAmplitude = (20 * log10(super.getMaxAmplitude() / (2*10e-5)))
        return dbAmplitude.toInt()
//        return super.getMaxAmplitude() / 50
    }
}