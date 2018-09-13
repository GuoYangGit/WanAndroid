package com.guoyang.commonres.config

import android.widget.ImageView
import com.bumptech.glide.Glide
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
                    .placeholder(android.R.color.white)
                    .error(android.R.color.white)
                    .into(imageView)
        } else {
            Glide.with(BaseApplication.instance())
                    .load(url)
                    .bitmapTransform(CropCircleTransformation(imageView.context))
                    .placeholder(android.R.color.white)
                    .error(android.R.color.white)
                    .into(imageView)
        }
    }
}