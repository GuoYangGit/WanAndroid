package com.guoyang.commonsdk.utils

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.content.ContextCompat
import com.guoyang.easymvvm.BuildConfig
import com.guoyang.easymvvm.helper.extens.toast
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/***
 * You may think you know what the following code does.
 * But you dont. Trust me.
 * Fiddle with it, and youll spend many a sleepless
 * night cursing the moment you thought youd be clever
 * enough to "optimize" the code below.
 * Now close this file and go play with something else.
 */
/***
 *
 *   █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 *  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 *  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 *  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 *           ░     ░ ░      ░  ░
 *
 * Created by guoyang on 2018/9/21.
 * github https://github.com/GuoYangGit
 * QQ:352391291
 */
fun Context.toastFail(error: Throwable?) {
    error?.let { throwable ->
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
        }
        if (throwable is SocketTimeoutException) {
            throwable.message?.let { toast("网络连接超时") }
        } else if (throwable is UnknownHostException || throwable is ConnectException) {
            throwable.message?.let { toast("网络未连接") }
        } else if (throwable is JSONException) {
            throwable.message?.let { toast("数据解析错误") }
        } else {
            throwable.message?.let { toast(it) }
        }
    }
}

fun Context.getCompactColor(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Context.dpToPx(@DimenRes resID: Int): Int = this.resources.getDimensionPixelOffset(resID)