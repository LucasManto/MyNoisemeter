package com.android.example.meuruidometro.fragments

import android.media.MediaRecorder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.android.example.meuruidometro.NoiseMeter
import com.android.example.meuruidometro.R
import java.util.*


class NoiseMeterFragment : Fragment() {

    private val LOG_TAG = "TEST"

    private var i: Float = 0f

    private lateinit var noiseMeterBackground: LinearLayoutCompat
    private lateinit var textIntensity: TextView
    private lateinit var recordButton: Button
    private lateinit var heightController: View

    private var timer: Timer? = null
    private var noiseMeter: NoiseMeter? = null
    private var recording: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_noisemeter, container, false)
    }

//    override fun onResume() {
//        super.onResume()
//        if ((activity as MainActivity).checkPermissionsAreGranted())
//            startRecording()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noiseMeterBackground = view.findViewById(R.id.noise_meter_background)
        textIntensity = view.findViewById(R.id.text_intensity)
        recordButton = view.findViewById(R.id.record_button)
        heightController = view.findViewById(R.id.height_controller)

        recordButton.setOnClickListener {

            when (recording) {
                false -> {
                    startRecording()
                }
                true -> {
                    stopRecording()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        stopRecording()
        recording = false
    }

    private fun startRecording() {
        recordButton.text = getString(R.string.stop_recording)

        noiseMeter = NoiseMeter("${context?.externalCacheDir?.absolutePath}/audio.3gp")
        noiseMeter?.startRecording()

        timer = Timer()
        timer?.scheduleAtFixedRate(RecorderTask(noiseMeter), 0, 500)

        recording = true
    }

    private fun stopRecording() {
        recordButton.text = getString(R.string.start_recording)

        noiseMeter?.stopRecording()

        timer?.cancel()

        recording = false
    }

    private inner class RecorderTask(private val recorder: MediaRecorder?) : TimerTask() {

        override fun run() {
            if (recorder != null) {
                activity?.runOnUiThread {
                    val dbValue = recorder.maxAmplitude
                    textIntensity.text = String.format("%2d DB", if (dbValue > 0) dbValue else 0)

                    heightController.updateLayoutParams<LinearLayoutCompat.LayoutParams> {
                        // TODO: Define max and min to normalize value between 0 and 1
                        // weight = dbValue / (max - min)
                    }
                }
            }
        }
    }
}
