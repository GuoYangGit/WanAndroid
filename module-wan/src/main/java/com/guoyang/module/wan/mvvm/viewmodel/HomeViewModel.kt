package com.guoyang.module.wan.mvvm.viewmodel

import android.databinding.ObservableArrayList
import com.guoyang.easymvvm.base.BaseViewModel
import com.guoyang.easymvvm.helper.extens.bindHttp
import com.guoyang.easymvvm.helper.extens.bindStatus
import com.guoyang.module.wan.mvvm.model.data.ArticleList
import com.guoyang.module.wan.mvvm.model.data.BannerBean
import com.guoyang.module.wan.mvvm.model.data.BaseBean
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

class HomeViewModel @Inject constructor(private val remote: Repo) : BaseViewModel() {
    val observableList = ObservableArrayList<ArticlesItemModel>()
    val bannerBean = ObservableArrayList<BannerBean>()
    val bannerImages = ObservableArrayList<String>()
    val bannerTitles = ObservableArrayList<String>()
    private var page: Int = 0

    fun loadData(isRefresh: Boolean): Single<BaseBean<ArticleList>> {
        if (isRefresh) page = 0
        return remote.getHomeList(page)
                .bindHttp()
                .doOnSuccess { bean ->
                    page++
                    if (isRefresh) observableList.clear()
                    bean.data.datas.let { list ->
                        list.map {
                            ArticlesItemModel(it)
                        }.let {
                            observableList.addAll(it)
                        }
                    }
                }
                .bindStatus(isRefresh, pageState, listState)
    }

    fun getBanner(): Single<BaseBean<List<BannerBean>>> {
        return remote.getBanner()
                .bindHttp()
                .doOnSuccess { bean ->
                    bannerImages.clear()
                    bannerTitles.clear()
                    bannerBean.clear()
                    bean.data.forEach {
                        bannerBean.add(it)
                        bannerTitles.add(it.title)
                        bannerImages.add(it.imagePath)
                    }
                }
    }
}