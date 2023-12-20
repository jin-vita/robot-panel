package org.techtown.robotpanel

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

object AppData {
    const val BASE_URL_ROBOT = "https://119.6.3.91:40023/moms/v1/rcs/blood/"
    const val BASE_URL_DOOR = "https://119.6.3.91:40018/moms/v1/"
    var isDebug = true
    fun debug(tag: String, msg: String) {
        if (isDebug) Log.d(tag, msg)
    }

    fun error(tag: String, msg: String) {
        if (isDebug) Log.e(tag, msg)
    }

    fun error(tag: String, msg: String, ex: Exception) {
        if (isDebug) Log.e(tag, msg, ex)
    }

    private lateinit var toast: Toast
    fun showToast(context: Context, msg: String) {
        if (::toast.isInitialized) toast.cancel()
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast.show()
    }

    private var seqCode = 0

    /**
     * 요청 코드 생성
     */
    fun generateRequestCode(): String {
        val format = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.KOREAN)
        seqCode += 1
        if (seqCode > 999) seqCode = 1

        var seqCodeStr = seqCode.toString()
        if (seqCodeStr.length == 1) {
            seqCodeStr = "00$seqCodeStr"
        } else if (seqCodeStr.length == 2) {
            seqCodeStr = "0$seqCodeStr"
        }

        val date = Date()
        val dateStr = format.format(date)

        return dateStr + seqCodeStr
    }
}