package ab.gitdemo.utils

import ab.gitdemo.R
import ab.gitdemo.ui.base.ActBase
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher

class UtilTextWatcher(context: ActBase, inputText: TextInputLayout, tag: Int) : TextWatcher {

    private var context: ActBase? = null
    private var tag = 0
    private var inputText: TextInputLayout? = null

    init {
        this.context = context
        this.tag = tag
        this.inputText = inputText
    }

    override fun afterTextChanged(s: Editable?) {
        if (s.isNullOrEmpty()) return

        when (tag) {
            R.string.empty_field->{
                if (!s.toString().trim().isNullOrEmpty()) {
                    inputText?.error = null
                } else
                    inputText?.error = context?.getString(R.string.error_field_required)
            }
        }

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
}