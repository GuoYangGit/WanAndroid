package com.guoyang.module.wan.mvvm.view.activity

import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.OvershootInterpolator
import com.guoyang.commonsdk.utils.NavigationUtil
import com.guoyang.commonsdk.utils.toastFail
import com.guoyang.easymvvm.base.BaseActivity
import com.guoyang.easymvvm.helper.annotation.PageStateType
import com.guoyang.easymvvm.helper.extens.bindStatusOrLifeCycle
import com.guoyang.easymvvm.helper.extens.set
import com.guoyang.easymvvm.helper.listener.RefreshPresenter
import com.guoyang.module.wan.R
import com.guoyang.module.wan.databinding.WanActivitySearchBinding
import com.guoyang.recyclerviewbindingadapter.ItemClickPresenter
import com.guoyang.recyclerviewbindingadapter.adapter.SingleTypeAdapter
import com.guoyang.recyclerviewbindingadapter.animators.ScaleInItemAnimator
import com.guoyang.module.wan.mvvm.viewmodel.SearchViewModel

import com.guoyang.module.wan.mvvm.viewmodel.ArticlesItemModel

class SearchActivity : BaseActivity<WanActivitySearchBinding>(), RefreshPresenter, ItemClickPresenter<ArticlesItemModel> {
    private var searchMsg: String = ""
    private val MIN_DELAY_TIME = 3000
    private var lastSearchTime: Long = 0

    private val mViewModel: SearchViewModel by lazy {
        createVM(SearchViewModel::class.java)
    }


    companion object {
        const val SEARCH_MSG = "search_msg"
    }

    override fun onItemClick(v: View, item: ArticlesItemModel) {
        NavigationUtil.toWebActivity(item.link, item.title)
    }

    override fun loadData(isRefresh: Boolean) {
        loadVMData(isRefresh)
    }

    private val mAdapter by lazy {
        SingleTypeAdapter(this, R.layout.wan_item_home, mViewModel.observableList).apply {
            this.itemPresenter = this@SearchActivity
            this.itemAnimator = ScaleInItemAnimator(interpolator = OvershootInterpolator())
        }
    }

    override fun getLayoutId(): Int = R.layout.wan_activity_search

    override fun initView() {
        mBinding.run {
            vm = mViewModel
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
                .bindStatusOrLifeCycle(isRefresh, mViewModel, this)
                .subscribe({}, {
                    toastFail(it)
                })
    }
}
