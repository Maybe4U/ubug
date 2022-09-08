package com.xk.tablet.ubug

import android.content.ContentValues
import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,
 * 有该类来接管程序,并记录发送错误报告.
 * 需要在Application中注册，为了要在程序启动器就监控整个程序。
 * @author g09312
 */
class UBug : Thread.UncaughtExceptionHandler {

    val TAG = "UBug"

    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null
    private lateinit var mContext: Context

    companion object {
        val instance by lazy(LazyThreadSafetyMode.NONE) {
            UBug()
        }
    }

    /**
     * 初始化
     */
    fun init(context: Context) {
        mContext = context
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            try {
                Thread.sleep(3000)
                //退出程序
                mDefaultHandler!!.uncaughtException(thread, ex)
            } catch (e: Exception) {
                Log.e(TAG, "error : ", e)
            }
        }
    }

    private fun handleException(e: Throwable): Boolean {
        //使用Toast来显示异常信息
        object : Thread() {
            override fun run() {
                Looper.prepare()
                /**
                 * realse 不提示
                 */
                Toast.makeText(mContext, "程序出现异常,即将退出...", Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
        }.start()
        //保存日志文件
        pushCrashLog(e)
        return true
    }

    /**
     * 将捕获的导致崩溃的错误信息发送给开发人员
     * 目前只将log日志保存在sdcard 和输出到LogCat中，并未发送给后台。
     */
    private fun pushCrashLog(ex: Throwable?) {
        Log.e(ContentValues.TAG, "pushCrash: $ex")
        if (null != ex) {
            pushCrash(mContext, ex)
        }
    }
}