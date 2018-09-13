package com.guoyang.commonres.view.extens

import android.databinding.BindingAdapter
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import com.classic.common.MultipleStatusView
import com.guoyang.commonres.config.ImageUtils
import com.guoyang.easymvvm.helper.annotation.PageStateType
import com.guoyang.easymvvm.helper.annotation.RefreshType
import com.guoyang.easymvvm.helper.listener.RefreshPresenter
import com.guoyang.commonres.view.banner.GlideImageLoader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer


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

@BindingAdapter(value = ["url", "avatar"], requireAll = false)
fun bindUrl(imageView: ImageView, url: String?, isAvatar: Boolean?) {
    ImageUtils.load(url, imageView, isAvatar = isAvatar ?: false)
}

@BindingAdapter(value = ["status"])
fun bindStatus(multipleStatusView: MultipleStatusView, @PageStateType stateType: Int?) {
    when (stateType) {
        PageStateType.LOADING -> multipleStatusView.showLoading()
        PageStateType.EMPTY -> multipleStatusView.showEmpty()
        PageStateType.ERROR -> multipleStatusView.showError()
        PageStateType.NORMAL -> multipleStatusView.showContent()
        PageStateType.CONTENT -> multipleStatusView.showContent()
    }
}

@BindingAdapter(value = ["onRefresh"])
fun bindOnRefresh(smartRefreshLayout: SmartRefreshLayout, listListener: RefreshPresenter?) {
    smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
        override fun onLoadMore(refreshLayout: RefreshLayout) {
            listListener?.loadData(false)
        }

        override fun onRefresh(refreshLayout: RefreshLayout) {
            listListener?.loadData(true)
        }

    })
}

@BindingAdapter(value = ["refreshing"])
fun bindRefreshing(smartRefreshLayout: SmartRefreshLayout, @RefreshType refreshType: Int?) {
    when (refreshType) {
        RefreshType.REFRESH -> smartRefreshLayout.finishRefresh()
        RefreshType.LOADMORE -> smartRefreshLayout.finishLoadMore()
        RefreshType.NOTMORE -> smartRefreshLayout.finishLoadMoreWithNoMoreData()
        RefreshType.REFRESHFAIL -> smartRefreshLayout.finishRefresh(false)
        RefreshType.LOADMOREFAIL -> smartRefreshLayout.finishLoadMore(false)
        RefreshType.NORMAL -> {
        }
    }
}

@BindingAdapter(value = ["bannerImages", "bannerTitles"])
fun bindBanner(banner: Banner, bannerImages: List<String>?, bannerTitles: List<String>?) {
    banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
    banner.setBannerAnimation(Transformer.CubeOut)
    banner.setImageLoader(GlideImageLoader())
    banner.setImages(bannerImages)
    banner.setBannerTitles(bannerTitles)
    banner.start()
}

@BindingAdapter(value = ["textHtml"])
fun bindTextHtml(textView: TextView, msg: String?) {
    if (msg != null && msg.contains("<em class='highlight'>") && msg.contains("</em>")) {
        val newMsg = msg
                .replace("<em class='highlight'>", "<strong><font color='#FF0000'>")
                .replace("</em>", "</font></strong>")
        textView.text = Html.fromHtml(newMsg)
    } else {
        textView.text = msg
    }
}