package com.guoyang.wanandroid.mvvm.view.fragment

import android.graphics.Color
import android.view.Gravity
import android.view.View
import com.guoyang.common.base.BaseFragment
import com.guoyang.smileview.SmileView
import com.guoyang.wanandroid.mvvm.viewmodel.MyViewModel

import com.guoyang.wanandroid.R
import com.guoyang.wanandroid.databinding.FragmentMyBinding
import com.gyf.barlibrary.ImmersionBar
import com.zyyoona7.popup.EasyPopup

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

class MyFragment : BaseFragment<FragmentMyBinding, MyViewModel>() {
    private val scorePopup: EasyPopup by lazy {
        EasyPopup.create()
                .setContentView(mContext, R.layout.pop_score)
                .setAnimationStyle(R.style.BottomPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                //变暗的背景颜色
                .setDimColor(Color.BLACK)
                .apply()
    }

    companion object {
        fun newInstance(): MyFragment {
            return MyFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_my

    override fun initView() {
        ImmersionBar
                .setTitleBar(activity, mBinding.toolbar)
        mBinding.listener = this
    }

    override fun initData() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.floatBtn -> {
                scorePopup.findViewById<SmileView>(R.id.smileView).setNum(60, 40)
                scorePopup.showAtLocation(v, Gravity.CENTER, 0, 0)
            }
        }
    }
}