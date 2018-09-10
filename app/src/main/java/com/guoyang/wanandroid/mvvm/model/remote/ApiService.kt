package com.guoyang.wanandroid.mvvm.model.remote

import com.guoyang.wanandroid.mvvm.model.data.*
import io.reactivex.Single
import retrofit2.http.*


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

interface ApiService {
    @GET("article/list/{page}/json")
    fun getHomeList(@Path("page") page: Int): Single<BaseBean<ArticleList>>

    @GET("banner/json")
    fun getBanner(): Single<BaseBean<List<BannerBean>>>

    @GET("tree/json")
    fun getNavigationList(): Single<BaseBean<List<NavigationList>>>

    @GET("article/list/{page}/json")
    fun getArticlesList(@Path("page") page: Int, @Query("cid") cid: Int): Single<BaseBean<ArticleList>>

    @GET("hotkey/json")
    fun getHotSearchList(): Single<BaseBean<List<HotSearchBean>>>

    @GET("friend/json")
    fun getWebUrlList(): Single<BaseBean<List<WebUrlBean>>>

    @FormUrlEncoded
    @POST("article/query/{page}/json")
    fun getSearchList(@Path("page") page: Int, @Field("k") searchMsg: String): Single<BaseBean<ArticleList>>
}