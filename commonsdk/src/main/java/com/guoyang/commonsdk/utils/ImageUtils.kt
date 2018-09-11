package com.guoyang.commonsdk.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.guoyang.commonsdk.R
import com.guoyang.easymvvm.base.BaseApplication
import jp.wasabeef.glide.transformations.CropCircleTransformation

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

object ImageUtils {
    fun load(url: String?, imageView: ImageView, isAvatar: Boolean = false) {
        if (!isAvatar) {
            Glide.with(BaseApplication.instance())
                    .load(url)
                    .placeholder(R.color.white_color)
                    .error(R.color.white_color)
                    .into(imageView)
        } else {
            Glide.with(BaseApplication.instance())
                    .load(url)
                    .bitmapTransform(CropCircleTransformation(imageView.context))
                    .placeholder(R.color.white_color)
                    .error(R.color.white_color)
                    .into(imageView)
        }
    }
}