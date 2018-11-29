package ab.gitdemo.ui.login

import ab.gitdemo.R
import ab.gitdemo.ui.base.FragBase
import ab.gitdemo.ui.repolist.FragRepoList
import ab.gitdemo.utils.Constants
import ab.gitdemo.utils.UtilTextWatcher
import ab.gitdemo.utils.Utils
import ab.gitdemo.webapi.model.UserData
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.pixplicity.easyprefs.library.Prefs
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.frag_login.*

class FragLogin : FragBase(),
        ISignInView,
        View.OnClickListener,
        TextView.OnEditorActionListener {

    private var signInPresenter: ISignInPresenterImpl? = null

    private var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_login, container, false)
    }

    override fun setUpView(view: View) {
        signInPresenter = ISignInPresenterImpl(this, getActBase())

        btnDone.setOnClickListener(this)
        txtUsername.setOnClickListener(this)

        btnDone.visibility = View.VISIBLE
        Utils.animation(btnDone, getActBase(), R.anim.fade_in_and_scale_in)

        ipTxtUsername.visibility = View.VISIBLE
        Utils.animation(ipTxtUsername, getActBase(), R.anim.fade_in)

        txtUsername.addTextChangedListener(UtilTextWatcher(getActBase(), ipTxtUsername, R.string.empty_field))
    }

    override fun onClick(v: View?) {
        when (v) {
            btnDone -> {
                onDoneClicked()
            }
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        return if (actionId == EditorInfo.IME_ACTION_DONE) {
            onDoneClicked()
            true
        } else {
            false
        }
    }

    override fun errorInvalidUserName(description: Int) {
        Utils.hideKeyboard(txtUsername)
        onError(description)
    }

    override fun goToHomeScreen() {
        Utils.hideKeyboard(txtUsername)
    }

    override fun successValidUserName() {
        if (Utils.isConnectedToNetwork(getActBase())) {
            disposable = signInPresenter?.fetchedUserDetails(getActBase(), txtUsername.text.toString().trim())
        } else {
            onError(R.string.no_internet)
        }
    }

    override fun onErrorFetchedUserDetail(error: Throwable) {
        onError(error.localizedMessage)
    }

    override fun fetchedUserDetails(result: UserData?) {
        if(result != null){
            Prefs.putBoolean(Constants.isLoggedIn, true)
            Prefs.putString(Constants.username, result.login)
            val  bundle = Bundle()
            bundle.putString(Constants.username, result.login)
            replaceFragment(FragRepoList(), bundle)
        }else{
            onError("Something went wrong!! Please check your entered username")
        }
    }

    private fun onDoneClicked() {
        signInPresenter?.validateCredentials(txtUsername.text.toString().trim())
    }

    override fun onPause() {
        super.onPause()
        if (disposable != null && !disposable?.isDisposed!!){
            disposable!!.dispose()
        }
    }
}