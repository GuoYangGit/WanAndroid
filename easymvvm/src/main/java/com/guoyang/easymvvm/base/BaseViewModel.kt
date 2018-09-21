package com.guoyang.easymvvm.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.guoyang.easymvvm.helper.annotation.PageStateType
import com.guoyang.easymvvm.helper.annotation.RefreshType
import com.guoyang.easymvvm.helper.extens.set
import timber.log.Timber

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

abstract class BaseViewModel : ViewModel() {
    @PageStateType
    val pageState = MutableLiveData<Int>()
    @RefreshType
    val listState = MutableLiveData<Int>()

    init {
        pageState.set(PageStateType.NORMAL)
        listState.set(RefreshType.NORMAL)
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("${javaClass.simpleName}:onCleared()")
    }
}