package com.guoyang.module.wan.core

import android.app.Application
import android.content.Context
import android.support.v4.app.FragmentManager
import com.guoyang.easymvvm.integration.AppLifecycles
import com.guoyang.easymvvm.integration.ConfigModule

/***
 * 该类是整个module可以对整个App的Application/Activity/Fragment的生命周期进行逻辑注入
 * 例如我们平时的第三方代码就可以在这里去进行实现
 **/

class GlobalConfiguration : ConfigModule {
    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycles>) {
        //lifecycles.add(AppLifecyclesImpl())
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>) {
        //lifecycles.add(ActivityLifecycleCallbacksImpl())
    }

    override fun injectFragmentLifecycle(context: Context, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>) {
        //lifecycles.add(FragmentLifecycleCallbacksImpl())
    }

}
