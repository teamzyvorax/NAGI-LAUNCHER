package com.nagi.launcher

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.StatFs
import java.io.File

data class RuntimeDeviceInfo(
    val primaryAbi: String,
    val supportsArm64: Boolean,
    val totalRamMb: Long,
    val freeStorageMb: Long,
    val gameDirectory: File,
    val runtimeDirectory: File
)

object RuntimeEnvironment {
    fun inspect(context: Context): RuntimeDeviceInfo {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val gameDir = File(context.filesDir, "minecraft")
        val runtimeDir = File(context.filesDir, "runtimes")
        gameDir.mkdirs()
        runtimeDir.mkdirs()

        val storage = StatFs(context.filesDir.absolutePath)
        return RuntimeDeviceInfo(
            primaryAbi = Build.SUPPORTED_ABIS.firstOrNull() ?: "Unknown",
            supportsArm64 = Build.SUPPORTED_ABIS.contains("arm64-v8a"),
            totalRamMb = memoryInfo.totalMem / (1024L * 1024L),
            freeStorageMb = storage.availableBytes / (1024L * 1024L),
            gameDirectory = gameDir,
            runtimeDirectory = runtimeDir
        )
    }
}
