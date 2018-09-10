package com.guoyang.common.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.guoyang.common.BR
import com.guoyang.common.helper.listener.Listener
import dagger.android.AndroidInjection
import javax.inject.Inject

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
 * Created by guoyang on 2018/8/20.
 * github https://github.com/GuoYangGit
 * QQ:352391291
 */

abstract class BaseActivity<B : ViewDataBinding, V : ViewModel> : AppCompatActivity(), IView, Listener {
    protected lateinit var mBinding: B
    @Inject
    protected lateinit var mViewModel: V


    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var modelClass: V

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        super.onCreate(savedInstanceState)
        initBinding()
        initView()
        initData()
    }

    private fun initBinding() {
        mViewModel = ViewModelProviders.of(this, factory).get(modelClass.javaClass)
        mBinding.setVariable(BR.vm, mViewModel)
        mBinding.setLifecycleOwner(this)
    }

    override fun onClick(v: View?) {

    }
}