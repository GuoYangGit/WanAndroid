package com.guoyang.module.wan.mvvm.viewmodel

import android.databinding.ObservableArrayList
import com.guoyang.easymvvm.base.BaseViewModel
import com.guoyang.easymvvm.helper.extens.bindHttp
import com.guoyang.easymvvm.helper.network.EmptyException
import com.guoyang.module.wan.mvvm.model.data.ArticleList
import com.guoyang.module.wan.mvvm.model.data.BaseBean
import com.guoyang.module.wan.mvvm.model.repository.Repo
import io.reactivex.Single

import javax.inject.Inject

class ArticlesViewModel @Inject constructor(private val remote: Repo) : BaseViewModel() {
    val observableList = ObservableArrayList<ArticlesItemModel>()

    private var page: Int = 0

    fun loadData(id: Int, isRefresh: Boolean): Single<BaseBean<ArticleList>> {
        if (isRefresh) page = 0
        return remote.getArticlesList(page, id)
                .bindHttp(500)
                .doOnSuccess { bean ->
                    page++
                    if (isRefresh) observableList.clear()
                    bean.data.datas.let { list ->
                        if (list.isEmpty()) throw EmptyException()
                        list.map {
                            ArticlesItemModel(it)
                        }.let {
                            observableList.addAll(it)
                        }
                    }
                }
    }
}
