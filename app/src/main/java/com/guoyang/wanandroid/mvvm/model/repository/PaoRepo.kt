package com.guoyang.wanandroid.mvvm.model.repository

import com.guoyang.wanandroid.mvvm.model.data.*
import com.guoyang.wanandroid.mvvm.model.remote.ApiService
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

class PaoRepo @Inject constructor(private val remote: ApiService) {
    fun getHomeList(page: Int): Single<BaseBean<ArticleList>> = remote.getHomeList(page)

    fun getBanner(): Single<BaseBean<List<BannerBean>>> = remote.getBanner()

    fun getNavigationList(): Single<BaseBean<List<NavigationList>>> = remote.getNavigationList()

    fun getArticlesList(page: Int, cid: Int): Single<BaseBean<ArticleList>> = remote.getArticlesList(page, cid)

    fun getHotSearchList(): Single<BaseBean<List<HotSearchBean>>> = remote.getHotSearchList()

    fun getWebUrlList(): Single<BaseBean<List<WebUrlBean>>> = remote.getWebUrlList()

    fun getSearchList(page: Int, searchMsg: String): Single<BaseBean<ArticleList>> = remote.getSearchList(page, searchMsg)
}