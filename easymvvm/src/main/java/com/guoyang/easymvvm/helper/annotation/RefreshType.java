package com.guoyang.easymvvm.helper.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.guoyang.easymvvm.helper.annotation.RefreshType.LOADMORE;
import static com.guoyang.easymvvm.helper.annotation.RefreshType.LOADMOREFAIL;
import static com.guoyang.easymvvm.helper.annotation.RefreshType.NORMAL;
import static com.guoyang.easymvvm.helper.annotation.RefreshType.NOTMORE;
import static com.guoyang.easymvvm.helper.annotation.RefreshType.REFRESH;
import static com.guoyang.easymvvm.helper.annotation.RefreshType.REFRESHFAIL;

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

@IntDef({REFRESH,LOADMORE,REFRESHFAIL,LOADMOREFAIL,NOTMORE,NORMAL})
@Retention(RetentionPolicy.SOURCE)
public @interface RefreshType {
    int REFRESH = -5;
    int LOADMORE = -4;
    int REFRESHFAIL = -3;
    int LOADMOREFAIL = -2;
    int NOTMORE = -1;
    int NORMAL = 0;
}
