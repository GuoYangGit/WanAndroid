package com.guoyang.commonres.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import com.guoyang.commonres.BR
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

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
 * Created by guoyang on 2018/8/31.
 * github https://github.com/GuoYangGit
 * QQ:352391291
 */
class BindingFlowAdapter<T> constructor(private val context: Context, @LayoutRes private val layoutRes: Int, list: ObservableArrayList<T>) : TagAdapter<T>(list) {
    override fun getView(parent: FlowLayout, position: Int, t: T): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(context), layoutRes, parent, false)
        binding.setVariable(BR.vm, t)
        binding.executePendingBindings()
        return binding.root
    }

    init {
        list.addOnListChangedCallback(
                object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
                    override fun onChanged(sender: ObservableList<T>?) {
                        notifyDataChanged()
                    }

                    override fun onItemRangeRemoved(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
                        notifyDataChanged()
                    }

                    override fun onItemRangeMoved(sender: ObservableList<T>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                        notifyDataChanged()
                    }

                    override fun onItemRangeInserted(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
                        notifyDataChanged()
                    }

                    override fun onItemRangeChanged(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
                        notifyDataChanged()
                    }

                }
        )
    }
}