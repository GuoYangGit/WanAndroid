package com.guoyang.module.wan.mvvm.view.fragment

import android.content.Intent
import com.guoyang.easymvvm.base.BaseFragment
import com.guoyang.module.wan.mvvm.viewmodel.ToolViewModel

import com.guoyang.commonres.view.adapter.BindingFlowAdapter
import com.guoyang.commonsdk.utils.NavigationUtil
import com.guoyang.commonsdk.utils.toastFail
import com.guoyang.module.wan.R
import com.guoyang.module.wan.databinding.WanFragmentToolBinding
import com.guoyang.module.wan.mvvm.view.activity.SearchActivity
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

class ToolFragment : BaseFragment<WanFragmentToolBinding>() {

    private val mViewModel: ToolViewModel by lazy {
        createVM(ToolViewModel::class.java)
    }

    companion object {
        fun newInstance(): ToolFragment {
            return ToolFragment()
        }
    }

    private val mApiUrlAdapter by lazy {
        BindingFlowAdapter(mContext, R.layout.wan_item_flow, mViewModel.observableWebUrl)
    }

    private val mHotSearchAdapter by lazy {
        BindingFlowAdapter(mContext, R.layout.wan_item_flow, mViewModel.observableHotSearch)
    }


    override fun getLayoutId(): Int = R.layout.wan_fragment_tool

    override fun initView() {
        ImmersionBar
                .setTitleBar(activity, mBinding.toolbar)

        mBinding.run {
            vm = mViewModel
            apiUrlFlow.run {
                adapter = mApiUrlAdapter
                setOnTagClickListener { _, position, _ ->
                    NavigationUtil.toWebActivity(mViewModel.observableWebUrl[position].link, mViewModel.observableWebUrl[position].title)
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