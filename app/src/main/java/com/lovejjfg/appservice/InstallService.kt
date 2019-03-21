package com.lovejjfg.appservice

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager

/**
 * Created by joe on 2018/12/5.
 * Email: lovejjfg@gmail.com
 */
class InstallService : JobService() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        println("onStopJob:$params")
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        println("onStartJob:$params")
        val installReceiver = InstallReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED)
        intentFilter.addAction(Intent.ACTION_INSTALL_PACKAGE)
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        intentFilter.addDataScheme("package")
        registerReceiver(installReceiver, intentFilter)
        return true
    }

    class InstallReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            println("onReceive:${intent?.action}")
        }
    }
}
