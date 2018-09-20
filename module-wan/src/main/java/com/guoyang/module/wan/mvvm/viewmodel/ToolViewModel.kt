package com.guoyang.module.wan.mvvm.viewmodel

import android.databinding.ObservableArrayList
import com.guoyang.easymvvm.base.BaseViewModel
import com.guoyang.easymvvm.helper.extens.bindHttp
import com.guoyang.module.wan.mvvm.model.data.BaseBean
import com.guoyang.module.wan.mvvm.model.data.HotAndWebBean
import com.guoyang.module.wan.mvvm.model.data.HotSearchBean
import com.guoyang.module.wan.mvvm.model.data.WebUrlBean
import com.guoyang.module.wan.mvvm.model.repository.Repo
import io.reactivex.Single
import io.reactivex.functions.BiFunction
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

class ToolViewModel @Inject constructor(private val remote: Repo) : BaseViewModel() {
    val observableWebUrl = ObservableArrayList<FlowItemViewModel>()
    val observableHotSearch = ObservableArrayList<FlowItemViewModel>()

    fun loadData(): Single<BaseBean<HotAndWebBean>> {
        val hotSearchList: Single<BaseBean<List<HotSearchBean>>> = remote.getHotSearchList()
        val webUrlList: Single<BaseBean<List<WebUrlBean>>> = remote.getWebUrlList()
        return Single.zip(hotSearchList, webUrlList,
                BiFunction<BaseBean<List<HotSearchBean>>, BaseBean<List<WebUrlBean>>, BaseBean<HotAndWebBean>> { t1, t2 ->
                    BaseBean(HotAndWebBean(t1.data, t2.data), t1.errorCode, t1.errorMsg)
                })
                .bindHttp()
                .doOnSuccess { baseBean ->
                    baseBean.let { bean ->
                        observableWebUrl.clear()
                        observableHotSearch.clear()
                        bean.data.webUrlList.map {
                            FlowItemViewModel(it.id, it.name, it.link)
                        }.let {
                            observableWebUrl.addAll(it)
                        }
                        bean.data.hotSearchList.map {
                            FlowItemViewModel(it.id, it.name, it.link)
                        }.let {
                            observableHotSearch.addAll(it)
                        }
                    }
                }
    }
}