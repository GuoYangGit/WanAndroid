package com.guoyang.commonsdk.core

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.guoyang.commonsdk.R
import com.gyf.barlibrary.ImmersionBar
import timber.log.Timber

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
 * Created by guoyang on 2018/9/11.
 * github https://github.com/GuoYangGit
 * QQ:352391291
 */
class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Timber.i("${activity.javaClass.simpleName} onActivityCreated")
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

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        ImmersionBar.with(activity).destroy()
    }
}