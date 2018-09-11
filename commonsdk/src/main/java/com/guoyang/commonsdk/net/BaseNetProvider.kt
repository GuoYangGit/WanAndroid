package com.guoyang.commonsdk.net

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.guoyang.commonsdk.BuildConfig
import com.guoyang.easymvvm.helper.network.ApiException
import com.guoyang.easymvvm.helper.network.NetProvider
import com.guoyang.easymvvm.helper.network.RequestHandler
import okhttp3.*

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

class BaseNetProvider(private val context: Context) : NetProvider {
    companion object {
        const val CONNECT_TIME_OUT: Long = 30
        const val READ_TIME_OUT: Long = 30
        const val WRITE_TIME_OUT: Long = 30
    }

    override fun configInterceptors(): Array<Interceptor>? = null

    override fun configHttps(builder: OkHttpClient.Builder) {
    }

    override fun configCookie(): CookieJar? =
            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))

    override fun configHandler(): RequestHandler = HeaderHandler()


    override fun configConnectTimeoutSecs(): Long = CONNECT_TIME_OUT

    override fun configReadTimeoutSecs(): Long = READ_TIME_OUT

    override fun configWriteTimeoutSecs(): Long = WRITE_TIME_OUT

    override fun configLogEnable(): Boolean = BuildConfig.DEBUG

    inner class HeaderHandler : RequestHandler {
        override fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request {
            return request
        }

        override fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response {
            var e: ApiException? = null
            when {
                401 == response.code() -> throw ApiException("登录已过期,请重新登录!")
                403 == response.code() -> e = ApiException("禁止访问!")
                404 == response.code() -> e = ApiException("链接错误")
                503 == response.code() -> e = ApiException("服务器升级中!")
                response.code() > 300 -> {
                    val message = response.body()!!.string()
                    e = if (message.isEmpty()) {
                        ApiException("服务器内部错误!")
                    } else {
                        ApiException(message)
                    }
                }
            }
            if (e != null) {
                throw e
            }
            return response
        }
    }
}