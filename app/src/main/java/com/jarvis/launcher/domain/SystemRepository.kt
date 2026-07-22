package com.jarvis.launcher.domain

import android.app.ActivityManager
import android.content.*
import android.os.BatteryManager
import android.os.Environment
import android.os.StatFs
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemRepository @Inject constructor(@ApplicationContext private val context: Context) {
    fun stats(): SystemStats {
        val battery = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val am = context.getSystemService(ActivityManager::class.java); val mem = ActivityManager.MemoryInfo().also(am::getMemoryInfo)
        val ram = "${((mem.totalMem - mem.availMem) / 1_048_576)} / ${(mem.totalMem / 1_048_576)} MB"
        val stat = StatFs(Environment.getDataDirectory().path); val used = (stat.totalBytes - stat.availableBytes) / 1_073_741_824; val total = stat.totalBytes / 1_073_741_824
        return SystemStats(battery.coerceAtLeast(0), ram, "$used / $total GB", System.getProperty("os.arch") ?: "ARM")
    }
}
