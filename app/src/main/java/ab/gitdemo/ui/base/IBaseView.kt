package ab.gitdemo.ui.base

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.View

interface IBaseView {

    //Misc methods
    fun showProgress()

    fun hideProgress()

    fun onError(@StringRes resId: Int)

    fun onError(message: String?)

    fun showMessage(message: String)

    fun showMessage(@StringRes resId: Int)

    fun isNetworkConnected(): Boolean

    fun hideKeyboard()

    //Actvity methods
    fun <T> changeActivity(classType: Class<T>?, bundle: Bundle?)


    //Fragment methods
    fun replaceFragment(frag: Fragment, bundle: Bundle?){}

    fun replaceFragment(frag: Fragment, bundle: Bundle?, hashMap :  HashMap<String, View>){}

    fun addFragment(frag: Fragment, bundle: Bundle?){}

    fun initFrag(frag: Fragment){}

    fun clearBackstack()


    //Dialog fragment
    fun showDialogFragment(frag: DialogFragment, tag: String){}

    fun dismissDialogFragment(frag: DialogFragment){}
}