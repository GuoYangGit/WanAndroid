package com.guoyang.wanandroid.mvvm.viewmodel

import android.databinding.ObservableArrayList
import com.guoyang.common.base.BaseViewModel
import com.guoyang.common.helper.extens.bindHttp
import com.guoyang.wanandroid.helper.extens.bindStatus
import com.guoyang.wanandroid.mvvm.model.data.ArticleList
import com.guoyang.wanandroid.mvvm.model.data.BannerBean
import com.guoyang.wanandroid.mvvm.model.data.BaseBean
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

class HomeViewModel @Inject constructor(private val remote: PaoRepo) : BaseViewModel() {
    val observableList = ObservableArrayList<ArticlesItemModel>()
    val bannerBean = ObservableArrayList<BannerBean>()
    val bannerImages = ObservableArrayList<String>()
    val bannerTitles = ObservableArrayList<String>()
    private var page: Int = 0

    fun loadData(isRefresh: Boolean): Single<BaseBean<ArticleList>> {
        if (isRefresh) page = 0
        return remote.getHomeList(page)
                .bindHttp()
                .doOnSuccess {
                    page++
                    if (isRefresh) observableList.clear()
                    it.data.datas.let {
                        it.map {
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
                .doOnSuccess {
                    bannerImages.clear()
                    bannerTitles.clear()
                    bannerBean.clear()
                    it.data.forEach {
                        bannerBean.add(it)
                        bannerTitles.add(it.title)
                        bannerImages.add(it.imagePath)
                    }
                }
    }
}