package com.guoyang.module.wan.mvvm.view.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import com.guoyang.commonsdk.utils.NavigationUtil
import com.guoyang.easymvvm.base.BaseActivity
import com.guoyang.easymvvm.helper.annotation.PageStateType
import com.guoyang.easymvvm.helper.extens.bindLifeCycle
import com.guoyang.easymvvm.helper.extens.set
import com.guoyang.easymvvm.helper.extens.toastFail
import com.guoyang.easymvvm.helper.listener.RefreshPresenter
import com.guoyang.module.wan.R
import com.guoyang.module.wan.databinding.WanActivityArticlesBinding
import com.guoyang.recyclerviewbindingadapter.ItemClickPresenter
import com.guoyang.recyclerviewbindingadapter.adapter.SingleTypeAdapter
import com.guoyang.recyclerviewbindingadapter.animators.ScaleInItemAnimator
import com.guoyang.module.wan.mvvm.viewmodel.ArticlesViewModel

import com.guoyang.module.wan.mvvm.viewmodel.ArticlesItemModel

class ArticlesActivity : BaseActivity<WanActivityArticlesBinding>(), RefreshPresenter, ItemClickPresenter<ArticlesItemModel> {
    override fun loadData(isRefresh: Boolean) {
        loadVMData(isRefresh)
    }

    private var mId: Int = 0

    private val mViewModel: ArticlesViewModel by lazy {
        createVM(ArticlesViewModel::class.java)
    }

    companion object {
        const val ARTICLES_ID = "articles_id"
        const val ARTICLES_TITLE = "articles_TITLE"
    }

    private val mAdapter by lazy {
        SingleTypeAdapter(this, R.layout.wan_item_home, mViewModel.observableList).apply {
            this.itemPresenter = this@ArticlesActivity
            this.itemAnimator = ScaleInItemAnimator(interpolator = OvershootInterpolator())
        }
    }

    override fun onItemClick(v: View, item: ArticlesItemModel) {
        NavigationUtil.toWebActivity(item.link, item.title)
    }

    override fun getLayoutId(): Int = R.layout.wan_activity_articles

    override fun initView() {
        mId = intent.getIntExtra(ARTICLES_ID, 0)
        findViewById<TextView>(R.id.toolbar_title).text = intent.getStringExtra(ARTICLES_TITLE)
        mBinding.run {
            vm = mViewModel
            refreshPresenter = this@ArticlesActivity
            recyclerView.run {
                this.layoutManager = LinearLayoutManager(this@ArticlesActivity)
                this.adapter = mAdapter
            }
        }
    }

    override fun initData() {
        mViewModel.pageState.set(PageStateType.LOADING)
        loadVMData(true)
    }

    private fun loadVMData(isRefresh: Boolean) {
        mViewModel.loadData(mId, isRefresh)
                .bindLifeCycle(this)
                .subscribe({}, {
                    toastFail(it)
                })
    }
}
