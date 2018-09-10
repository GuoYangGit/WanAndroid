package com.guoyang.wanandroid.mvvm.viewmodel

import android.databinding.ObservableArrayList
import com.guoyang.common.base.BaseViewModel
import com.guoyang.common.helper.extens.bindHttp
import com.guoyang.wanandroid.mvvm.model.data.BaseBean
import com.guoyang.wanandroid.mvvm.model.data.NavigationList
import com.guoyang.wanandroid.mvvm.model.repository.PaoRepo
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

class NavigationViewModel @Inject constructor(private val remote: PaoRepo) : BaseViewModel() {
    val mTitleObservableList = ObservableArrayList<NavigationItemTitleViewModel>()
    val mFlowRvObservableList = ObservableArrayList<ObservableArrayList<FlowItemViewModel>>()

    fun loadData(): Single<BaseBean<List<NavigationList>>> {
        return remote.getNavigationList()
                .bindHttp()
                .doOnSuccess {
                    it.data.map {
                        NavigationItemTitleViewModel(it)
                    }.map {
                        mTitleObservableList.add(it)
                        it.item
                    }.map {
                        it.map {
                            FlowItemViewModel(it.id, it.name)
                        }.let {
                            val list:ObservableArrayList<FlowItemViewModel> = ObservableArrayList()
                            list.addAll(it)
                            mFlowRvObservableList.add(list)
                        }
                    }
                }
    }
}
