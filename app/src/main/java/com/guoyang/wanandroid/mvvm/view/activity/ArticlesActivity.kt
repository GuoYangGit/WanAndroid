package com.guoyang.wanandroid.mvvm.view.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import com.guoyang.common.base.BaseActivity
import com.guoyang.common.helper.annotation.PageStateType
import com.guoyang.common.helper.extens.bindLifeCycle
import com.guoyang.common.helper.extens.set
import com.guoyang.common.helper.extens.toastFail
import com.guoyang.common.helper.listener.ListListener
import com.guoyang.common.helper.recyclerview.ItemClickPresenter
import com.guoyang.common.helper.recyclerview.adapter.SingleTypeAdapter
import com.guoyang.common.helper.recyclerview.animators.ScaleInItemAnimator
import com.guoyang.wanandroid.mvvm.viewmodel.ArticlesViewModel

import com.guoyang.wanandroid.R
import com.guoyang.wanandroid.databinding.ActivityArticlesBinding
import com.guoyang.wanandroid.mvvm.viewmodel.ArticlesItemModel

class ArticlesActivity : BaseActivity<ActivityArticlesBinding, ArticlesViewModel>(), ListListener, ItemClickPresenter<ArticlesItemModel> {
    override fun loadData(isRefresh: Boolean) {
        loadVMData(isRefresh)
    }

    private var mId: Int = 0

    companion object {
        const val ARTICLES_ID = "articles_id"
        const val ARTICLES_TITLE = "articles_TITLE"
    }

    private val mAdapter by lazy {
        SingleTypeAdapter(this, R.layout.item_home, mViewModel.observableList).apply {
            this.itemPresenter = this@ArticlesActivity
            this.itemAnimator = ScaleInItemAnimator(interpolator = OvershootInterpolator())
        }
    }

    override fun onItemClick(v: View, item: ArticlesItemModel) {
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra(WebActivity.URL, item.link)
        intent.putExtra(WebActivity.TITLE, item.title)
        startActivity(intent)
    }

    override fun getLayoutId(): Int = R.layout.activity_articles

    override fun initView() {
        mId = intent.getIntExtra(ARTICLES_ID, 0)
        findViewById<TextView>(R.id.toolbar_title).text = intent.getStringExtra(ARTICLES_TITLE)
        mBinding.listListener = this
        mBinding.recyclerView.run {
            this.layoutManager = LinearLayoutManager(this@ArticlesActivity)
            this.adapter = mAdapter
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
