package com.guoyang.wanandroid.mvvm.viewmodel

import android.databinding.ObservableArrayList
import com.guoyang.common.base.BaseViewModel
import com.guoyang.common.helper.extens.bindHttp
import com.guoyang.wanandroid.helper.extens.bindStatus
import com.guoyang.wanandroid.mvvm.model.data.ArticleList
import com.guoyang.wanandroid.mvvm.model.data.BaseBean
import com.guoyang.wanandroid.mvvm.model.repository.PaoRepo
import io.reactivex.Single

import javax.inject.Inject

class SearchViewModel @Inject constructor(private val remote: PaoRepo) : BaseViewModel() {
    val observableList = ObservableArrayList<ArticlesItemModel>()
    private var page: Int = 0

    fun loadData(isRefresh: Boolean, searchMsg: String): Single<BaseBean<ArticleList>> {
        if (isRefresh) page = 0
        return remote.getSearchList(page, searchMsg)
                .bindHttp()
                .doOnSuccess {
                    page++
                    if (isRefresh) observableList.clear()
                    it.data.datas.let {
                        it.map {
                            ArticlesItemModel(it)
                        }.let {
                            observableList.addAll(it)
                        }
                    }
                }
                .bindStatus(isRefresh, pageState, listState)
    }
}
