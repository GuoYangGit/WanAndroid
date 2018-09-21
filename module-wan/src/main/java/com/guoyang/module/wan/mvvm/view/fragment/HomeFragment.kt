package com.guoyang.module.wan.mvvm.view.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.OvershootInterpolator
import com.guoyang.commonsdk.utils.NavigationUtil
import com.guoyang.commonsdk.utils.toastFail
import com.guoyang.easymvvm.base.BaseFragment
import com.guoyang.easymvvm.helper.annotation.PageStateType
import com.guoyang.easymvvm.helper.extens.bindLifeCycle
import com.guoyang.easymvvm.helper.extens.bindStatusOrLifeCycle
import com.guoyang.easymvvm.helper.extens.set
import com.guoyang.easymvvm.helper.listener.RefreshPresenter
import com.guoyang.module.wan.R
import com.guoyang.module.wan.databinding.WanFragmentHomeBinding
import com.guoyang.recyclerviewbindingadapter.ItemClickPresenter
import com.guoyang.recyclerviewbindingadapter.adapter.SingleTypeAdapter
import com.guoyang.recyclerviewbindingadapter.animators.ScaleInItemAnimator
import com.guoyang.module.wan.mvvm.view.activity.SearchActivity
import com.guoyang.module.wan.mvvm.viewmodel.ArticlesItemModel
import com.guoyang.module.wan.mvvm.viewmodel.HomeViewModel
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

class HomeFragment : BaseFragment<WanFragmentHomeBinding>(), RefreshPresenter, ItemClickPresenter<ArticlesItemModel> {

    private val mViewModel: HomeViewModel by lazy {
        createVM(HomeViewModel::class.java)
    }

    override fun onItemClick(v: View, item: ArticlesItemModel) {
        NavigationUtil.toWebActivity(item.link, item.title)
    }

    override fun loadData(isRefresh: Boolean) {
        loadVMData(isRefresh)
    }

    private val mAdapter by lazy {
        SingleTypeAdapter(mContext, R.layout.wan_item_home, mViewModel.observableList).apply {
            this.itemPresenter = this@HomeFragment
            this.itemAnimator = ScaleInItemAnimator(interpolator = OvershootInterpolator())
        }
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.wan_fragment_home

    override fun initView() {
        ImmersionBar
                .setTitleBar(activity, mBinding.toolbar)
        mBinding.run {
            vm = mViewModel
            refreshPresenter = this@HomeFragment
            clickPresenter = this@HomeFragment
            recyclerView.run {
                this.layoutManager = LinearLayoutManager(mContext)
                this.adapter = mAdapter
            }
            banner.setOnBannerListener {
                mViewModel.bannerBean[it]?.run {
                    NavigationUtil.toWebActivity(url, title)
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
                .bindStatusOrLifeCycle(isRefresh, mViewModel, this)
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