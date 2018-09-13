package com.guoyang.wanandroid.mvvm.view.fragment

import android.content.Intent
import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.guoyang.easymvvm.base.BaseFragment
import com.guoyang.easymvvm.helper.extens.toastFail
import com.guoyang.wanandroid.mvvm.viewmodel.NavigationViewModel

import com.guoyang.wanandroid.R
import com.guoyang.wanandroid.databinding.FragmentNavigationBinding
import com.guoyang.wanandroid.databinding.ItemNavigationFlowBinding
import com.guoyang.commonres.view.adapter.BindingFlowAdapter
import com.guoyang.recyclerviewbindingadapter.ItemClickPresenter
import com.guoyang.recyclerviewbindingadapter.ItemDecorator
import com.guoyang.recyclerviewbindingadapter.adapter.BindingViewHolder
import com.guoyang.recyclerviewbindingadapter.adapter.SingleTypeAdapter
import com.guoyang.recyclerviewbindingadapter.divider.RecyclerViewDivider
import com.guoyang.wanandroid.mvvm.view.activity.ArticlesActivity
import com.guoyang.wanandroid.mvvm.viewmodel.NavigationItemTitleViewModel
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

class NavigationFragment : BaseFragment<FragmentNavigationBinding, NavigationViewModel>(), ItemClickPresenter<NavigationItemTitleViewModel> {
    override fun onItemClick(v: View, item: NavigationItemTitleViewModel) {
    }

    companion object {
        fun newInstance(): NavigationFragment {
            return NavigationFragment()
        }
    }

    private val mTitleAdapter by lazy {
        SingleTypeAdapter(mContext, R.layout.item_navigation_title, mViewModel.mTitleObservableList).apply {
            this.itemPresenter = this@NavigationFragment
        }
    }

    private val mFlowRvAdapter by lazy {
        SingleTypeAdapter(mContext, R.layout.item_navigation_flow, mViewModel.mFlowRvObservableList).apply {
            itemDecorator = object : ItemDecorator {
                override fun decorator(holder: BindingViewHolder<ViewDataBinding>, position: Int, viewType: Int) {
                    (holder.binding as ItemNavigationFlowBinding).itemFlowLl.run {
                        val itemPosition = position
                        this.adapter = BindingFlowAdapter(mContext, R.layout.item_flow, mViewModel.mFlowRvObservableList[itemPosition])
                        this.setOnTagClickListener { _, position, _ ->
                            val intent = Intent(mContext, ArticlesActivity::class.java)
                            intent.putExtra(ArticlesActivity.ARTICLES_ID, mViewModel.mFlowRvObservableList[itemPosition][position].id)
                            intent.putExtra(ArticlesActivity.ARTICLES_TITLE, mViewModel.mFlowRvObservableList[itemPosition][position].title)
                            mContext.startActivity(intent)
                            return@setOnTagClickListener true
                        }
                    }
                }

            }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_navigation

    override fun initView() {
        ImmersionBar
                .setTitleBar(activity, mBinding.toolbar)
        mBinding.run {
            titleRv.run {
                layoutManager = LinearLayoutManager(mContext)
                adapter = mTitleAdapter
                addItemDecoration(RecyclerViewDivider.builder().build())
            }
            flowRv.run {
                layoutManager = LinearLayoutManager(mContext)
                adapter = mFlowRvAdapter
                addItemDecoration(RecyclerViewDivider.builder().build())
            }
        }
    }

    override fun initData() {
        mViewModel.loadData()
                .subscribe({}, {
                    context?.toastFail(it)
                })
    }
}