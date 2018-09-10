package com.guoyang.wanandroid.mvvm.view.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.OvershootInterpolator
import com.guoyang.common.base.BaseFragment
import com.guoyang.common.helper.annotation.PageStateType
import com.guoyang.common.helper.extens.bindLifeCycle
import com.guoyang.common.helper.extens.set
import com.guoyang.common.helper.extens.toastFail
import com.guoyang.common.helper.listener.ListListener
import com.guoyang.common.helper.recyclerview.ItemClickPresenter
import com.guoyang.common.helper.recyclerview.adapter.SingleTypeAdapter
import com.guoyang.common.helper.recyclerview.animators.ScaleInItemAnimator
import com.guoyang.wanandroid.R
import com.guoyang.wanandroid.databinding.FragmentHomeBinding
import com.guoyang.wanandroid.mvvm.view.activity.SearchActivity
import com.guoyang.wanandroid.mvvm.view.activity.WebActivity
import com.guoyang.wanandroid.mvvm.viewmodel.ArticlesItemModel
import com.guoyang.wanandroid.mvvm.viewmodel.HomeViewModel
import com.gyf.barlibrary.ImmersionBar

/***
 *
 *   █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 *  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 *  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 *  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 *           ░     ░ ░      ░  ░
 *
 * Created by guoyang on 2018/8/22.
 * github https://github.com/GuoYangGit
 * QQ:352391291
 */

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), ListListener, ItemClickPresenter<ArticlesItemModel> {

    override fun onItemClick(v: View, item: ArticlesItemModel) {
        val intent = Intent(mContext, WebActivity::class.java)
        intent.putExtra(WebActivity.URL, item.link)
        intent.putExtra(WebActivity.TITLE, item.title)
        mContext.startActivity(intent)
    }

    override fun loadData(isRefresh: Boolean) {
        loadVMData(isRefresh)
    }

    private val mAdapter by lazy {
        SingleTypeAdapter(mContext, R.layout.item_home, mViewModel.observableList).apply {
            this.itemPresenter = this@HomeFragment
            this.itemAnimator = ScaleInItemAnimator(interpolator = OvershootInterpolator())
        }
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        ImmersionBar
                .setTitleBar(activity, mBinding.toolbar)
        mBinding.run {
            listListener = this@HomeFragment
            listener = this@HomeFragment
            recyclerView.run {
                this.layoutManager = LinearLayoutManager(mContext)
                this.adapter = mAdapter
            }
            banner.setOnBannerListener {
                mViewModel.bannerBean[it]?.run {
                    val intent = Intent(mContext, WebActivity::class.java)
                    intent.putExtra(WebActivity.URL, url)
                    intent.putExtra(WebActivity.TITLE, title)
                    mContext.startActivity(intent)
                }
            }
        }
    }

    override fun initData() {
        mViewModel.pageState.set(PageStateType.LOADING)
        loadVMData(true)
    }

    private fun loadVMData(isRefresh: Boolean) {
        mViewModel.loadData(isRefresh)
                .bindLifeCycle(this)
                .subscribe({}, {
                    context?.toastFail(it)
                })
        if (isRefresh)
            mViewModel.getBanner()
                    .bindLifeCycle(this)
                    .subscribe({}, {
                        context?.toastFail(it)
                    })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.search_iv -> {
                val intent = Intent(mContext, SearchActivity::class.java)
                mContext.startActivity(intent)
            }
        }
    }
}