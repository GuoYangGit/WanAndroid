package com.guoyang.module.wan.di.module

import android.app.Application
import com.guoyang.commonsdk.net.BaseNetProvider
import com.guoyang.commonsdk.net.HttpConstants
import com.guoyang.easymvvm.helper.network.NetMgr
import com.guoyang.module.wan.mvvm.model.remote.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/***
 * 该类提供整个module需要的实例，比如网络请求，数据库等等(记得加上@Provide，@Single注解)
 * includes的module提供Activity/Fragment/ViewModel的实例
 **/

@Module(includes = [
    ActivityModule::class,
    FragmentModule::class,
    ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideRemoteClient(application: Application): Retrofit = NetMgr.getRetrofit(HttpConstants.HOST_API, BaseNetProvider(application))

    @Provides
    @Singleton
    fun provideService(client: Retrofit) = client.create(ApiService::class.java)
}