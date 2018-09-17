package com.guoyang.wanandroid.mvvm.view.fragment

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.guoyang.easymvvm.base.BaseFragment
import com.guoyang.easymvvm.helper.extens.toastFail
import com.guoyang.wanandroid.mvvm.viewmodel.ToolViewModel

import com.guoyang.wanandroid.R
import com.guoyang.wanandroid.databinding.FragmentToolBinding
import com.guoyang.commonres.view.adapter.BindingFlowAdapter
import com.guoyang.commonsdk.core.RouterHub
import com.guoyang.wanandroid.mvvm.view.activity.SearchActivity
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

class ToolFragment : BaseFragment<FragmentToolBinding, ToolViewModel>() {

    companion object {
        fun newInstance(): ToolFragment {
            return ToolFragment()
        }
    }

    private val mApiUrlAdapter by lazy {
        BindingFlowAdapter(mContext, R.layout.item_flow, mViewModel.observableWebUrl)
    }

    private val mHotSearchAdapter by lazy {
        BindingFlowAdapter(mContext, R.layout.item_flow, mViewModel.observableHotSearch)
    }


    override fun getLayoutId(): Int = R.layout.fragment_tool

    override fun initView() {
        ImmersionBar
                .setTitleBar(activity, mBinding.toolbar)

        mBinding.run {
            apiUrlFlow.run {
                adapter = mApiUrlAdapter
                setOnTagClickListener { _, position, _ ->
                    ARouter.getInstance().build(RouterHub.WEB_WEBACTIVITY)
                            .withString("url", mViewModel.observableWebUrl[position].link)
                            .withString("title", mViewModel.observableWebUrl[position].title)
                            .navigation()
                    return@setOnTagClickListener true
                }
            }

            hotSearchFlow.run {
                adapter = mHotSearchAdapter
                setOnTagClickListener { _, position, _ ->
                    val intent = Intent(mContext, SearchActivity::class.java)
                    intent.putExtra(SearchActivity.SEARCH_MSG, mViewModel.observableHotSearch[position].title)
                    mContext.startActivity(intent)
                    return@setOnTagClickListener true
                }
            }
        }
    }

    override fun initData() {
        mViewModel.loadData()
                .subscribe({}, {
                    mContext.toastFail(it)
                })
    }
}