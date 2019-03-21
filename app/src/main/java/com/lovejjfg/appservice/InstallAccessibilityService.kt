package com.lovejjfg.appservice

import android.accessibilityservice.AccessibilityService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityWindowInfo

/**
 * Created by joe on 2018/12/5.
 * Email: lovejjfg@gmail.com
 */
class InstallAccessibilityService : AccessibilityService() {
    override fun onInterrupt() {
        println("InstallAccessibilityService onInterrupt..")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        println("InstallAccessibilityService onUnbind....")
        return super.onUnbind(intent)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        println("InstallAccessibilityService:${event.toString()}")
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        return super.onKeyEvent(event)
    }

    override fun getWindows(): MutableList<AccessibilityWindowInfo> {
        return super.getWindows()
    }

//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        return Service.START_STICKY
//    }

    override fun onServiceConnected() {
        startJob()
    }

    fun startJob() {
        println("InstallAccessibilityService onStartJob:")
        val installReceiver = InstallReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED)
        intentFilter.addAction(Intent.ACTION_INSTALL_PACKAGE)
        intentFilter.addDataScheme("package")
        registerReceiver(installReceiver, intentFilter)
    }

    class InstallReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            println("InstallAccessibilityService onReceive:${intent?.action}")
//            if (intent != null && intent.action != Intent.ACTION_PACKAGE_REMOVED) {
            vibrate(context)
//            }
        }

        fun vibrate(context: Context, milliseconds: Long = 80) {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val canVibrate: Boolean = vibrator.hasVibrator()
            if (canVibrate) {
                if (VERSION.SDK_INT >= VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createWaveform(
                            longArrayOf(
                                milliseconds,
                                milliseconds,
                                milliseconds,
                                milliseconds,
                                milliseconds,
                                milliseconds
                            ), -1
                        )
                    )
                } else {
                    vibrator.vibrate(
                        longArrayOf(
                            milliseconds + 100,
                            milliseconds,
                            milliseconds + 100,
                            milliseconds
                        ), -1
                    )
                }
            }
        }
    }
}
