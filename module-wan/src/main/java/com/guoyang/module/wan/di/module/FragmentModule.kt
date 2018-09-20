package com.guoyang.module.wan.di.module

import com.guoyang.module.wan.mvvm.view.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

/***
 * 该类提供整个module中fragment的实例(这里需要注意所拥有该Fragment的Activity必须实现HasSupportFragmentInjector接口)
 * 例如:
 *   @ContributesAndroidInjector
 *   abstract fun contributeXXFragment(): XXFragment
 */

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeNavigationFragment(): NavigationFragment

    @ContributesAndroidInjector
    abstract fun contributeToolFragment(): ToolFragment

    @ContributesAndroidInjector
    abstract fun contributeMyFragment(): MyFragment
}