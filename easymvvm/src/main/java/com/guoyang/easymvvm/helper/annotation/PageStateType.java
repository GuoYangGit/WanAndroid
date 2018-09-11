package com.guoyang.easymvvm.helper.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.guoyang.easymvvm.helper.annotation.PageStateType.CONTENT;
import static com.guoyang.easymvvm.helper.annotation.PageStateType.EMPTY;
import static com.guoyang.easymvvm.helper.annotation.PageStateType.ERROR;
import static com.guoyang.easymvvm.helper.annotation.PageStateType.LOADING;
import static com.guoyang.easymvvm.helper.annotation.PageStateType.NORMAL;

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

@IntDef({LOADING, EMPTY, ERROR, NORMAL,CONTENT})
@Retention(RetentionPolicy.SOURCE)
public @interface PageStateType {
    int LOADING = -3;
    int EMPTY = -2;
    int ERROR = -1;
    int NORMAL = 0;
    int CONTENT = 1;
}
