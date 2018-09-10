package com.guoyang.wanandroid.helper.extens

import android.arch.lifecycle.MutableLiveData
import com.guoyang.common.helper.annotation.RefreshType
import com.guoyang.common.helper.annotation.PageStateType
import com.guoyang.common.helper.extens.get
import com.guoyang.common.helper.extens.set
import io.reactivex.Single

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
 * Created by guoyang on 2018/8/22.
 * github https://github.com/GuoYangGit
 * QQ:352391291
 */

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