package com.guoyang.wanandroid.mvvm.view.activity

import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.OvershootInterpolator
import com.alibaba.android.arouter.launcher.ARouter
import com.guoyang.commonsdk.core.RouterHub
import com.guoyang.easymvvm.base.BaseActivity
import com.guoyang.easymvvm.helper.annotation.PageStateType
import com.guoyang.easymvvm.helper.extens.set
import com.guoyang.easymvvm.helper.extens.toastFail
import com.guoyang.easymvvm.helper.listener.RefreshPresenter
import com.guoyang.recyclerviewbindingadapter.ItemClickPresenter
import com.guoyang.recyclerviewbindingadapter.adapter.SingleTypeAdapter
import com.guoyang.recyclerviewbindingadapter.animators.ScaleInItemAnimator
import com.guoyang.wanandroid.mvvm.viewmodel.SearchViewModel

import com.guoyang.wanandroid.R
import com.guoyang.wanandroid.databinding.ActivitySearchBinding
import com.guoyang.wanandroid.mvvm.viewmodel.ArticlesItemModel

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(), RefreshPresenter, ItemClickPresenter<ArticlesItemModel> {
    private var searchMsg: String = ""
    private val MIN_DELAY_TIME = 3000
    private var lastSearchTime: Long = 0

    companion object {
        const val SEARCH_MSG = "search_msg"
    }

    override fun onItemClick(v: View, item: ArticlesItemModel) {
        ARouter.getInstance().build(RouterHub.WEB_WEBACTIVITY)
                .withString("url",item.link)
                .withString("title",item.title)
                .navigation()
    }

    override fun loadData(isRefresh: Boolean) {
        loadVMData(isRefresh)
    }

    private val mAdapter by lazy {
        SingleTypeAdapter(this, R.layout.item_home, mViewModel.observableList).apply {
            this.itemPresenter = this@SearchActivity
            this.itemAnimator = ScaleInItemAnimator(interpolator = OvershootInterpolator())
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun initView() {
        mBinding.run {
            refreshPresenter = this@SearchActivity
            recyclerView.run {
                layoutManager = LinearLayoutManager(this@SearchActivity)
                adapter = mAdapter
            }
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                    text?.let {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastSearchTime >= MIN_DELAY_TIME) {
                            searchMsg = it.toString()
                            mViewModel.pageState.set(PageStateType.LOADING)
                            loadVMData(true)
                        }
                        lastSearchTime = currentTime
                    }
                }

            })
        }
    }

    override fun initData() {
        searchMsg = intent.getStringExtra(SEARCH_MSG) ?: ""
        if (searchMsg.isNotEmpty()) {
            lastSearchTime = System.currentTimeMillis()
            mBinding.editText.setText(searchMsg)
            mViewModel.pageState.set(PageStateType.LOADING)
            loadVMData(true)
        }
    }

    private fun loadVMData(isRefresh: Boolean) {
        mViewModel.loadData(isRefresh, searchMsg)
                .subscribe({}, {
                    toastFail(it)
                })
    }
}
