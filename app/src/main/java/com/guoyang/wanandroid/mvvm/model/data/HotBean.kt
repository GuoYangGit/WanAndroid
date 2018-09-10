package com.guoyang.wanandroid.mvvm.model.data

import com.google.gson.annotations.SerializedName


/***
 * You may think you know what the following code does.
 * But you dont. Trust me.
 * Fiddle with it, and youll spend many a sleepless
 * night cursing the moment you thought youd be clever
 * enough to "optimize" the code below.
 * Now close this file and go play with something else.
 */
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
 * Created by guoyang on 2018/9/4.
 * github https://github.com/GuoYangGit
 * QQ:352391291
 */

data class WebUrlBean(
        @SerializedName("icon") val icon: String = "",
        @SerializedName("id") val id: Int = 0,
        @SerializedName("link") val link: String = "",
        @SerializedName("name") val name: String = "",
        @SerializedName("order") val order: Int = 0,
        @SerializedName("visible") val visible: Int = 0
)

data class HotSearchBean(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("link") val link: String = "",
        @SerializedName("name") val name: String = "",
        @SerializedName("order") val order: Int = 0,
        @SerializedName("visible") val visible: Int = 0
)

data class HotAndWebBean(
        @SerializedName("hotSearchList") val hotSearchList: List<HotSearchBean> = listOf(),
        @SerializedName("webUrlList") val webUrlList: List<WebUrlBean> = listOf()
)