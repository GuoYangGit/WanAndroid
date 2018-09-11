package com.guoyang.easymvvm.helper.extens

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.guoyang.easymvvm.BuildConfig
import com.guoyang.easymvvm.R
import com.guoyang.easymvvm.helper.annotation.PageStateType
import com.guoyang.easymvvm.helper.annotation.RefreshType
import com.guoyang.easymvvm.helper.annotation.ToastType
import com.guoyang.easymvvm.helper.network.ApiException
import com.guoyang.easymvvm.helper.network.IBaseBean
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import es.dmoral.toasty.Toasty
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

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
 * Created by guoyang on 2018/8/20.
 * github https://github.com/GuoYangGit
 * QQ:352391291
 */

fun <T> Single<T>.async(withDelay: Long = 0): Single<T> =
        this.subscribeOn(Schedulers.io())
                .delay(withDelay, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.bindHttp(withDelay: Long = 0): Single<T> {
    return this
            .async(withDelay)
            .doOnSuccess {
                if (it is IBaseBean) {
                    if (!it.isOk()) {
                        throw ApiException(it.getMsg())
                    }
                }
            }
}

fun <T> Single<T>.bindLifeCycle(owner: LifecycleOwner): SingleSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))

fun <T> Single<T>.bindStatus(isRefresh: Boolean = true, @PageStateType pageState: MutableLiveData<Int>? = null, @RefreshType listState: MutableLiveData<Int>? = null): Single<T> {
    return this
            .doOnSubscribe {
                pageState?.let {
                    if (it.get() == PageStateType.CONTENT) return@let
                    it.set(PageStateType.LOADING)
                }
            }
            .doOnSuccess {
                pageState?.let {
                    if (it.get() == PageStateType.CONTENT) return@let
                    it.set(PageStateType.CONTENT)
                }
                listState?.let {
                    if (isRefresh) {
                        it.set(RefreshType.REFRESH)
                    } else {
                        it.set(RefreshType.LOADMORE)
                    }
                }
            }
            .doOnError {
                pageState?.let {
                    if (it.get() == PageStateType.CONTENT) return@let
                    it.set(PageStateType.ERROR)
                }
                listState?.let {
                    if (isRefresh) {
                        it.set(RefreshType.REFRESHFAIL)
                    } else {
                        it.set(RefreshType.LOADMOREFAIL)
                    }
                }
            }
}

fun <T : Any> MutableLiveData<T>.set(value: T?) = postValue(value)

fun <T : Any> MutableLiveData<T>.get() = value

fun Context.getCompactColor(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Context.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT, @ToastType type: Int = ToastType.NORMAL) {
    if (msg.isEmpty()) return
    when (type) {
        ToastType.WARNING -> Toasty.warning(this, msg, duration, true).show()
        ToastType.ERROR -> Toasty.error(this, msg, duration, true).show()
        ToastType.NORMAL -> Toasty.info(this, msg, duration, false).show()
        ToastType.SUCCESS -> Toasty.success(this, msg, duration, true).show()
    }
}

fun Context.toastFail(error: Throwable?) {
    error?.let {
        if (BuildConfig.DEBUG) {
            it.printStackTrace()
        }
        if (it is SocketTimeoutException) {
            it.message?.let { toast("网络连接超时", ToastType.ERROR) }
        } else if (it is UnknownHostException || it is ConnectException) {
            it.message?.let { toast("网络未连接", ToastType.ERROR) }
        } else if (it is JSONException) {
            it.message?.let { toast("数据解析错误", ToastType.ERROR) }
        } else {
            it.message?.let { toast(it, ToastType.ERROR) }
        }
    }
}

fun AppCompatActivity.switchFragment(current: Fragment?, targetFg: Fragment, tag: String? = null) {
    val ft = supportFragmentManager.beginTransaction()
    current?.run { ft.hide(this) }
    if (!targetFg.isAdded) {
        tag?.run {
            ft.add(R.id.container, targetFg, tag)
        } ?: ft.add(R.id.container, targetFg)

    }
    ft.show(targetFg)
    ft.commitAllowingStateLoss()
}

fun Context.dpToPx(@DimenRes resID: Int): Int = this.resources.getDimensionPixelOffset(resID)