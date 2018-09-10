package com.guoyang.wanandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.guoyang.common.helper.network.NetMgr
import com.guoyang.common.integration.AppLifecycles
import com.guoyang.common.integration.ConfigModule
import com.guoyang.wanandroid.helper.BaseNetProvider
import com.gyf.barlibrary.ImmersionBar
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader

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

class GlobalConfiguration : ConfigModule {
    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)//全局设置主题颜色
            ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycles>) {
        lifecycles.add(object : AppLifecycles {
            override fun attachBaseContext(base: Context) {
                Log.i("Tag", "Application attachBaseContext")
            }

            override fun onCreate(application: Application) {
                Log.i("Tag", "${application.javaClass.simpleName} onCreate")
            }

            override fun onTerminate(application: Application) {
                Log.i("Tag", "${application.javaClass.simpleName} onTerminate")
            }

        })
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>) {
        lifecycles.add(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                ImmersionBar.with(activity).destroy()
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.i("Tag", "${activity.javaClass.simpleName} onActivityCreated")
                ImmersionBar.with(activity)
                        .init()
                val toolbar: Toolbar? = activity.findViewById(R.id.toolbar)
                toolbar?.run {
                    ImmersionBar
                            .setTitleBar(activity, this)
                    if (activity is AppCompatActivity) {
                        activity.setSupportActionBar(activity.findViewById(R.id.toolbar))
                        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            activity.setActionBar(activity.findViewById(R.id.toolbar))
                            activity.actionBar.setDisplayShowTitleEnabled(false)
                        } else {

                        }
                    }
                }
                val titleTv: TextView? = activity.findViewById(R.id.toolbar_title)
                titleTv?.text = activity.title
                val backIv: ImageView? = activity.findViewById(R.id.toolbar_back)
                backIv?.setOnClickListener {
                    activity.onBackPressed()
                }
            }

        })
    }

    override fun injectFragmentLifecycle(context: Context, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>) {
        lifecycles.add(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentCreated(fm: FragmentManager?, f: Fragment?, savedInstanceState: Bundle?) {
                super.onFragmentCreated(fm, f, savedInstanceState)
                Log.i("Tag", "${f?.javaClass?.simpleName} onFragmentCreated")
            }
        })
    }

}