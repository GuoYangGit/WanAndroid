package com.guoyang.easymvvm.helper.extens

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.guoyang.easymvvm.R
import com.guoyang.easymvvm.base.BaseViewModel
import com.guoyang.easymvvm.helper.annotation.PageStateType
import com.guoyang.easymvvm.helper.annotation.RefreshType
import com.guoyang.easymvvm.helper.network.ApiException
import com.guoyang.easymvvm.helper.network.EmptyException
import com.guoyang.easymvvm.helper.network.IBaseBean
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

fun <T> Single<T>.bindStatusOrLifeCycle(isRefresh: Boolean = true, viewModel: BaseViewModel, owner: LifecycleOwner): SingleSubscribeProxy<T> =
        this.bindStatus(isRefresh, viewModel)
                .bindLifeCycle(owner)

fun <T> Single<T>.bindHttp(withDelay: Long = 0): Single<T> {
    return this
            .async(withDelay)
            .doOnSuccess {
                if (it is IBaseBean && !it.isOk()) {
                    throw ApiException(it.msg)
                }
            }
}

fun <T> Single<T>.bindLifeCycle(owner: LifecycleOwner): SingleSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))

fun <T> Single<T>.bindStatus(isRefresh: Boolean = true, viewModel: BaseViewModel): Single<T> {
    return viewModel.run {
        this@bindStatus
                .doOnSubscribe {
                    if (pageState.get() != PageStateType.CONTENT)
                        pageState.set(PageStateType.LOADING)
                }
                .doOnSuccess {
                    if (pageState.get() != PageStateType.CONTENT)
                        pageState.set(PageStateType.CONTENT)
                    if (isRefresh) {
                        listState.set(RefreshType.REFRESH)
                    } else {
                        listState.set(RefreshType.LOADMORE)
                    }
                }
                .doOnError {
                    if (pageState.get() != PageStateType.CONTENT) {
                        if (it is EmptyException) {
                            pageState.set(PageStateType.EMPTY)
                        } else {
                            pageState.set(PageStateType.ERROR)
                        }
                    }
                    if (isRefresh) {
                        listState.set(RefreshType.REFRESHFAIL)
                    } else {
                        if (it is EmptyException) {
                            listState.set(RefreshType.NOTMORE)
                        } else {
                            listState.set(RefreshType.LOADMOREFAIL)
                        }
                    }
                }
    }
}

fun <T : Any> MutableLiveData<T>.set(value: T?) = postValue(value)

fun <T : Any> MutableLiveData<T>.get() = value


fun Context.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    if (msg.isEmpty()) return
    Toast.makeText(this, msg, duration).show()
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