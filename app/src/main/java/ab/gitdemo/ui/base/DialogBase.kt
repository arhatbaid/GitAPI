package ab.gitdemo.ui.base

import ab.gitdemo.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout

abstract class DialogBase : DialogFragment(), IDialogView {

    private var actBase: ActBase? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ActBase) {
            val mActivity = context as ActBase?
            this.actBase = mActivity
            mActivity!!.onFragmentAttached()
        }
    }

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
        dialog.window!!
            .attributes.windowAnimations = R.style.AppTheme_DialogAnimation
    }

    override fun onError(message: String?) {
        actBase.let {
            it!!.onError(message)
        }
    }

    override fun onError(@StringRes resId: Int) {
        actBase.let {
            it!!.onError(resId)
        }
    }

    override fun showMessage(message: String) {
        actBase.let {
            it!!.showMessage(message)
        }
    }

    override fun showMessage(@StringRes resId: Int) {
        actBase.let {
            it!!.showMessage(resId)
        }
    }

    override fun isNetworkConnected(): Boolean {
        return actBase.let {
            it!!.isNetworkConnected()
        }
    }

    override fun onDetach() {
        actBase = null
        super.onDetach()
    }

    override fun hideKeyboard() {
        actBase.let {
            it!!.hideKeyboard()
        }
    }

    override fun clearBackstack() {
        actBase.let {
            it!!.clearBackstack()
        }
    }

    protected abstract fun setUp(view: View)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // creating the fullscrdialogeen dialog
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
//            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)
    }

    override fun show(fragmentManager: FragmentManager, tag: String) {
        val transaction = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    override fun dismissDialog(tag: String) {
        dismiss()
        actBase.let {
            it!!.onFragmentDetached(tag)
        }
    }

    override fun showProgress() {
        actBase.let {
            it!!.showProgress()
        }
    }

    override fun hideProgress() {
        actBase.let {
            it!!.hideProgress()
        }
    }

    override fun <T> changeActivity(classType: Class<T>?, bundle: Bundle?) {
        actBase.let {
            changeActivity(classType, bundle)
        }
    }
}