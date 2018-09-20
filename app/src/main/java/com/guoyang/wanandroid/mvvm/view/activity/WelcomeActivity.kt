package com.guoyang.wanandroid.mvvm.view.activity

import com.guoyang.commonsdk.utils.NavigationUtil
import com.guoyang.easymvvm.base.BaseActivity
import com.guoyang.wanandroid.R
import com.guoyang.wanandroid.databinding.ActivityWelcomeBinding

/***
 * You may think you know what the following code does.
 * But you dont. Trust me.
 * Fiddle with it, and youll spend many a sleepless
 * night cursing the moment you thought youd be clever
 * enough to "optimize" the code below.
 * Now close this file and go play with something else.
 */
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
 * Created by guoyang on 2018/9/19.
 * github https://github.com/GuoYangGit
 * QQ:352391291
 */
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_welcome


    override fun initView() {
    }

    override fun initData() {
        NavigationUtil.toMainActivity()
        finish()
    }
}