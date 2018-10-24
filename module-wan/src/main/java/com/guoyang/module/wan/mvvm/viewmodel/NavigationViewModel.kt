package com.guoyang.module.wan.mvvm.viewmodel

import android.databinding.ObservableArrayList
import com.guoyang.easymvvm.base.BaseViewModel
import com.guoyang.easymvvm.helper.extens.bindHttp
import com.guoyang.module.wan.mvvm.model.data.BaseBean
import com.guoyang.module.wan.mvvm.model.data.NavigationList
import com.guoyang.module.wan.mvvm.model.repository.Repo
import io.reactivex.Single

import javax.inject.Inject

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

class NavigationViewModel @Inject constructor(private val remote: Repo) : BaseViewModel() {
    val mTitleObservableList = ObservableArrayList<NavigationItemTitleViewModel>()
    val mFlowRvObservableList = ObservableArrayList<ObservableArrayList<FlowItemViewModel>>()

    fun loadData(): Single<BaseBean<List<NavigationList>>> {
        return remote.getNavigationList()
                .bindHttp()
                .doOnSuccess { bean ->
                    bean.data.asSequence().map {
                        mTitleObservableList.add(NavigationItemTitleViewModel(it))
                        it.children
                    }.map { list ->
                        list.map {
                            FlowItemViewModel(it.id, it.name)
                        }.let {
                            val data: ObservableArrayList<FlowItemViewModel> = ObservableArrayList()
                            data.addAll(it)
                            mFlowRvObservableList.add(data)
                        }
                    }.toList()
                }
    }
}
