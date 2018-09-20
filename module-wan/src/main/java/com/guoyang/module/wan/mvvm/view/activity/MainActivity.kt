package com.guoyang.module.wan.mvvm.view.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.guoyang.commonsdk.core.RouterConstants
import com.guoyang.easymvvm.adapter.AbsViewPageAdapter
import com.guoyang.easymvvm.base.BaseActivity
import com.guoyang.module.wan.R
import com.guoyang.module.wan.databinding.WanActivityMainBinding
import com.guoyang.module.wan.mvvm.view.fragment.HomeFragment
import com.guoyang.module.wan.mvvm.view.fragment.MyFragment
import com.guoyang.module.wan.mvvm.view.fragment.NavigationFragment
import com.guoyang.module.wan.mvvm.view.fragment.ToolFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
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
 * Created by guoyang on 2018/8/22.
 * github https://github.com/GuoYangGit
 * QQ:352391291
 */
@Route(path = RouterConstants.WAN_MAINACTIVITY)
class MainActivity : BaseActivity<WanActivityMainBinding>(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    private lateinit var fragmentAdapter: FragmentStatePagerAdapter

    override fun initView() {
        fragmentAdapter = object : AbsViewPageAdapter(supportFragmentManager, arrayOf("首页", "导航", "最热", "我的")) {
            override fun getItem(position: Int): Fragment? {
                if (list[position] == null) {
                    list[position] = (return when (position) {
                        0 -> HomeFragment.newInstance()
                        1 -> NavigationFragment.newInstance()
                        2 -> ToolFragment.newInstance()
                        3 -> MyFragment.newInstance()
                        else -> HomeFragment.newInstance()
                    })
                }
                return list[position]
            }
        }
        mBinding.run {
            viewPager.adapter = fragmentAdapter
            bottomNavigation
                    .setMode(BottomNavigationBar.MODE_FIXED)
                    .setActiveColor(R.color.public_colorPrimary)
                    .setInActiveColor(R.color.public_black_color)
                    .setBarBackgroundColor(R.color.public_white_color)
                    .addItem(BottomNavigationItem(R.mipmap.icon_home, "首页"))
                    .addItem(BottomNavigationItem(R.mipmap.icon_navigation, "导航"))
                    .addItem(BottomNavigationItem(R.mipmap.icon_hot, "最热"))
                    .addItem(BottomNavigationItem(R.mipmap.icon_my, "我的"))
                    .setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
                        override fun onTabReselected(position: Int) {
                        }

                        override fun onTabUnselected(position: Int) {
                        }

                        override fun onTabSelected(position: Int) {
                            viewPager.setCurrentItem(position, false)
                        }

                    })
                    .initialise()
        }
    }

    override fun initData() {
    }

    override fun getLayoutId(): Int = R.layout.wan_activity_main

}
