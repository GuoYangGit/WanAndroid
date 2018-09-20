package com.guoyang.module.wan.mvvm.model.data
import com.google.gson.annotations.SerializedName


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

data class NavigationList(
        @SerializedName("children") val children: List<Children> = listOf(),
        @SerializedName("courseId") val courseId: Int = 0,
        @SerializedName("id") val id: Int = 0,
        @SerializedName("name") val name: String = "",
        @SerializedName("order") val order: Int = 0,
        @SerializedName("parentChapterId") val parentChapterId: Int = 0,
        @SerializedName("visible") val visible: Int = 0
)

data class Children(
    @SerializedName("children") val children: List<Any> = listOf(),
    @SerializedName("courseId") val courseId: Int = 0,
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("order") val order: Int = 0,
    @SerializedName("parentChapterId") val parentChapterId: Int = 0,
    @SerializedName("visible") val visible: Int = 0
)