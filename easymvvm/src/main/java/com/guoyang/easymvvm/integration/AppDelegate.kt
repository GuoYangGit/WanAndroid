package com.guoyang.easymvvm.integration

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

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
 * Created by guoyang on 2018/8/20.
 * github https://github.com/GuoYangGit
 * QQ:352391291
 */

class AppDelegate(context: Context) : AppLifecycles {
    private var mApplication: Application? = null
    private var mModules: List<ConfigModule>? = null
    private var mAppLifecycles: MutableList<AppLifecycles> = arrayListOf()
    private var mActivityLifecycles: MutableList<Application.ActivityLifecycleCallbacks> = arrayListOf()
    private var mFragmentLifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks> = arrayListOf()

    override fun attachBaseContext(base: Context) {
        mAppLifecycles.forEach {
            it.attachBaseContext(base)
        }
    }

    override fun onCreate(application: Application) {
        mApplication = application
        mApplication?.registerActivityLifecycleCallbacks(FragmentLifecycleCallbacks())
        mActivityLifecycles.forEach {
            mApplication?.registerActivityLifecycleCallbacks(it)
        }
        mAppLifecycles.forEach {
            if (mApplication != null) {
                it.onCreate(mApplication!!)
            }
        }
    }

    override fun onTerminate(application: Application) {
        mActivityLifecycles.forEach {
            mApplication?.unregisterActivityLifecycleCallbacks(it)
        }
        mAppLifecycles.forEach {
            if (mApplication != null) {
                it.onTerminate(mApplication!!)
            }
        }
    }

    private inner class FragmentLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            if (activity is FragmentActivity) {
                mFragmentLifecycles.forEach {
                    activity.supportFragmentManager
                            .registerFragmentLifecycleCallbacks(it, true)
                }
            }
        }

        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

    }

    init {
        mModules = ManifestParser(context).parse()
        mModules?.forEach {
            it.injectAppLifecycle(context, mAppLifecycles)
            it.injectActivityLifecycle(context, mActivityLifecycles)
            it.injectFragmentLifecycle(context, mFragmentLifecycles)
        }
    }
}