package com.guoyang.module.web.mvvm.view.activity

import android.annotation.SuppressLint
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.guoyang.commonsdk.core.RouterHub
import com.guoyang.easymvvm.base.BaseActivity
import com.guoyang.module.web.R
import com.guoyang.module.web.databinding.WebActivityWebBinding
import com.guoyang.module.web.mvvm.viewmodel.WebViewModel

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
@Route(path = RouterHub.WEB_WEBACTIVITY)
class WebActivity : BaseActivity<WebActivityWebBinding, WebViewModel>() {
    @Autowired(name = "url")
    @JvmField
    var url: String? = null
    @Autowired(name = "title")
    @JvmField
    var title: String? = null
    private var mAgentWeb: AgentWeb? = null

    override fun getLayoutId(): Int = R.layout.web_activity_web

    override fun initView() {
        ARouter.getInstance().inject(this)
        findViewById<TextView>(R.id.toolbar_title).text = title?:"百度"
        AgentWeb.with(this)
                .setAgentWebParent(mBinding.contentView, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url?:"https://www.baidu.com")
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
