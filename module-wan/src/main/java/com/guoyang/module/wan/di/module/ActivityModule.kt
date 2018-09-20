package com.guoyang.module.wan.di.module

import com.guoyang.module.wan.mvvm.view.activity.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

/***
 * 这里存放module中的Dagger需要注入的Activity的实例
 * 例如:
 *   @ContributesAndroidInjector
 *   abstract fun contributeXXActivity(): XXActivity
 */

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeArticlesActivity(): ArticlesActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchActivity(): SearchActivity
}