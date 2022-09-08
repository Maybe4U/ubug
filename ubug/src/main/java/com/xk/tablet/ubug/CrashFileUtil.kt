package com.xk.tablet.ubug

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


private val infos: Map<String, String> = HashMap() //用于存储设备信息与异常信息

private var mContext: Context? = null

fun bugTest() {
    val i = null
    Log.e(TAG, "bugTest: -----------$i")
}

fun pushCrash(context: Context, throwable: Throwable): String? {
    mContext = context
    val sb = StringBuilder()
    try {
        sb.append(getCurrentTime()).append("\n")
        for (pair in infos) {
            sb.append(pair.key).append("=").append(pair.value).append("\n")
        }
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        throwable.printStackTrace(printWriter)
        printWriter.flush()
        printWriter.close()
        val result: String = writer.toString()
        sb.append(result)
        return writeFile(sb.toString())
    } catch (e: Exception) {
        Log.e(TAG, "pushCrash: $e")
        sb.append("an error occured while writing file...\r\n")
        writeFile(sb.toString())
    }
    return null
}


private fun getCurrentTime(): String? {
    val simpleDateFormat = SimpleDateFormat()
    simpleDateFormat.applyPattern("yyyy-MM-dd-HH:mm:ss")
    return simpleDateFormat.format(Date())
}

@Throws(java.lang.Exception::class)
private fun writeFile(sb: String): String? {
    val time = getCurrentTime()
    val fileName = "/crash-$time.log"
    if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        val path: String? = getGlobalPath()
        Log.e(TAG, "writeFile: $path")
        val dir = File(path)
        if (!dir.exists()) {
            dir.mkdirs()
            Log.e(TAG, "writeFile: $dir")
        }

        //path+fileName 实际日志路径 /storage/emulated/0/Crash/crash-当前时间.log
        try {
            val fos = FileOutputStream(dir.toString() + File.separator + fileName, true)
            fos.write(sb.toByteArray())
            fos.flush()
            fos.close()
        } catch (e: Exception) {
            Log.e(TAG, "writeFile-------------------: $e")
        }
    }
    return fileName
}

private fun getGlobalPath(): String? {
    return mContext!!.getExternalFilesDir(null)!!.path +
            File.separator + "Crash"
}
