package com.guoyang.wanandroid.mvvm.view.activity

import android.annotation.SuppressLint
import android.widget.LinearLayout
import android.widget.TextView
import com.guoyang.easymvvm.base.BaseActivity
import com.guoyang.wanandroid.mvvm.viewmodel.WebViewModel

import com.guoyang.wanandroid.R
import com.guoyang.wanandroid.databinding.ActivityWebBinding
import com.just.agentweb.AgentWeb

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

class WebActivity : BaseActivity<ActivityWebBinding, WebViewModel>() {
    private var baseUrl: String? = null
    private var mAgentWeb: AgentWeb? = null

    companion object {
        const val URL = "url"
        const val TITLE = "title"
    }

    override fun getLayoutId(): Int = R.layout.activity_web

    override fun initView() {
        baseUrl = intent.getStringExtra(URL)
        findViewById<TextView>(R.id.toolbar_title).text = intent.getStringExtra(TITLE)
        AgentWeb.with(this)
                .setAgentWebParent(mBinding.contentView, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(baseUrl)
    }

    override fun initData() {
    }

    @SuppressLint("MissingSuperCall")
    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()

    }

    @SuppressLint("MissingSuperCall")
    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    @SuppressLint("MissingSuperCall")
    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }
}
