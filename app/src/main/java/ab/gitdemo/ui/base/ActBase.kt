package ab.gitdemo.ui.base


import ab.gitdemo.R
import ab.gitdemo.ui.repolist.FragRepoList
import ab.gitdemo.utils.Constants
import ab.gitdemo.utils.Utils
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.arhatbaid.boxmedemo.ui.startup.FragStartUp
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.act_base.*

open class ActBase : AppCompatActivity(),
        IBaseView,
        FragBase.Callback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.act_base)
        if (Prefs.getBoolean(Constants.isLoggedIn, false)) {
            initFrag(FragRepoList())
        } else {
            initFrag(FragStartUp())
        }
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun onError(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show()

    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }

    override fun showMessage(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show()

    }

    override fun isNetworkConnected(): Boolean {
        return Utils.isConnectedToNetwork(this)
    }

    override fun hideKeyboard() {
        Utils.hideKeyboard(this)
    }

    override fun <T> changeActivity(classType: Class<T>?, bundle: Bundle?) {
        val intent = Intent(this, classType)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }


    override fun replaceFragment(frag: Fragment, bundle: Bundle?) {
        val ft = supportFragmentManager?.beginTransaction()
        if (bundle != null) frag.arguments = bundle
        ft?.addToBackStack(frag::class.java.name)
        ft?.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left,
                R.anim.slide_in_right, R.anim.slide_out_right)
        ft?.replace(R.id.fragContainer, frag, frag::class.java.name)
        ft?.commit()
    }

    override fun replaceFragment(frag: Fragment, bundle: Bundle?, hashMap: HashMap<String, View>) {
        val ft = supportFragmentManager?.beginTransaction()
        if (bundle != null) frag.arguments = bundle
        ft?.addToBackStack(frag::class.java.name)
         for (pair in hashMap){
             ft?.addSharedElement(pair.value, pair.key)
             break
         }
        ft?.replace(R.id.fragContainer, frag, frag::class.java.name)
        ft?.commitAllowingStateLoss()
    }

    override fun clearBackstack() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun showDialogFragment(frag: DialogFragment, tag: String) {
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag(tag)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        frag.show(ft, tag)
        frag.isCancelable = false
    }

    override fun dismissDialogFragment(frag: DialogFragment) {
        frag.dismiss()
    }

    override fun addFragment(frag: Fragment, bundle: Bundle?) {
        val ft = supportFragmentManager?.beginTransaction()
        if (bundle != null) frag.arguments = bundle
        ft?.addToBackStack(frag::class.java.name)
        ft?.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left,
                R.anim.slide_in_right, R.anim.slide_out_right)
        ft?.add(R.id.fragContainer, frag, frag::class.java.name)
        ft?.commit()
    }

    override fun initFrag(frag: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragContainer, frag, frag::class.java.name)
                .commit()
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {
        Log.d(tag, "Detached")
    }


    override fun onBackPressed() {


        val fragList = supportFragmentManager.findFragmentByTag(FragRepoList::class.java.name)
        if (fragList != null && fragList.isVisible && supportFragmentManager.fragments.size == 1) {
            finish()
            return
        }
        super.onBackPressed()
    }


}