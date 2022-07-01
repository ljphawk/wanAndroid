package com.ljp.wanandroid.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


/*
 *@创建者       L_jp
 *@创建时间     2022/6/29 15:07.
 *@描述
 */

fun EditText.addTextChangedListener(listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s == null) {
                listener.invoke("")
            } else {
                listener.invoke(s.toString())
            }
        }

        override fun afterTextChanged(s: Editable?) {

        }

    })
}

