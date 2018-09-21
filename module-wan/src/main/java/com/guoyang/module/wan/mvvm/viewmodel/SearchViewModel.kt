package com.guoyang.module.wan.mvvm.viewmodel

import android.databinding.ObservableArrayList
import com.guoyang.easymvvm.base.BaseViewModel
import com.guoyang.easymvvm.helper.extens.bindHttp
import com.guoyang.module.wan.mvvm.model.data.ArticleList
import com.guoyang.module.wan.mvvm.model.data.BaseBean
import com.guoyang.module.wan.mvvm.model.repository.Repo
import io.reactivex.Single

import javax.inject.Inject

class SearchViewModel @Inject constructor(private val remote: Repo) : BaseViewModel() {
    val observableList = ObservableArrayList<ArticlesItemModel>()
    private var page: Int = 0

    fun loadData(isRefresh: Boolean, searchMsg: String): Single<BaseBean<ArticleList>> {
        if (isRefresh) page = 0
        return remote.getSearchList(page, searchMsg)
                .bindHttp()
                .doOnSuccess { bean ->
                    page++
                    if (isRefresh) observableList.clear()
                    bean.data.datas.let { list ->
                        list.map {
                            ArticlesItemModel(it)
                        }.let {
                            observableList.addAll(it)
                        }
                    }
                }
    }
}
