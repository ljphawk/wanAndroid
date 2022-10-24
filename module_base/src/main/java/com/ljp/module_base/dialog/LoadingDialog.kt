package com.ljp.module_base.dialog

import android.content.Context
import android.view.Gravity
import android.widget.ProgressBar
import android.widget.TextView
import com.ljp.module_base.R
import com.ljp.dialog.action.AnimAction
import com.ljp.lib_base.extensions.show
import com.ljp.dialog.base.BaseDialog

/*
 *@创建者       L_jp
 *@创建时间     2019/6/21 11:04.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
class LoadingDialog {
    class Builder(context: Context) : BaseDialog.Builder<Builder>(context) {
        private val mMessageView: TextView?
        private val mProgressView: ProgressBar?

        fun setMessage(text: CharSequence?): Builder {
            mMessageView?.text = text
            mMessageView?.show(!text.isNullOrEmpty())
            return this
        }

        init {
            setContentView(R.layout.layout_loading_dialog)
            setAnimStyle(AnimAction.ANIM_TOAST)
            setGravity(Gravity.CENTER)
            setBackgroundDimEnabled(false)
            setCancelable(false)
            mMessageView = findViewById(R.id.tv_dialog_wait_message)
            mProgressView = findViewById(R.id.progress_bar)
        }
    }
}