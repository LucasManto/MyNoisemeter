package com.android.example.meuruidometro.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.android.example.meuruidometro.R
import com.android.example.meuruidometro.adapters.CustomPagerAdapter
import com.android.example.meuruidometro.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView
    private lateinit var viewPager: ViewPager

    private val MEDIA_RECORDER_REQUEST = 0
    private val requiredPermissions = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_noisemeter -> {
                    viewPager.currentItem = 0
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_info -> {
                    viewPager.currentItem = 1
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navView = binding.navView
        viewPager = binding.pager

        viewPager.adapter = CustomPagerAdapter(supportFragmentManager)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        if (!checkPermissionsAreGranted())
                            requestAudioPermissions()
                        navView.selectedItemId = R.id.navigation_noisemeter
                    }
                    1 -> navView.selectedItemId = R.id.navigation_info
                }
            }
        })

        if (!checkPermissionsAreGranted())
            requestAudioPermissions()
    }

    fun checkPermissionsAreGranted(): Boolean {
        for (permission in requiredPermissions)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            )
                return false

        return true
    }

    private fun requestAudioPermissions() {
        ActivityCompat.requestPermissions(this, requiredPermissions, MEDIA_RECORDER_REQUEST)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MEDIA_RECORDER_REQUEST -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permissions granted", Toast.LENGTH_LONG).show()
                } else {
                    viewPager.currentItem = 0
                    navView.selectedItemId = R.id.navigation_info
                }
                return
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
