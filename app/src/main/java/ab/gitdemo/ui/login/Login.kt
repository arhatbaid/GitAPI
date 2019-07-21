package ab.gitdemo.ui.login

import ab.gitdemo.R
import ab.gitdemo.ui.base.ActBase
import ab.gitdemo.ui.base.IBaseView
import ab.gitdemo.utils.Constants
import ab.gitdemo.webapi.model.UserData
import ab.gitdemo.webapi.restapi.WebAPIClient
import android.os.Bundle
import android.text.TextUtils
import com.pixplicity.easyprefs.library.Prefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

interface Login {
    interface View : IBaseView {
        fun errorInvalidUserName(description: Int)

        fun successValidUserName()

        fun goToHomeScreen()

        fun openRepoListFrag(bundle: Bundle)

    }

    interface Presenter {

        fun validateCredentials(username: String)

        fun fetchedUserDetails(context: ActBase, username: String): Disposable
    }
}

class LoginPresenter(private var view: Login.View) : Login.Presenter {


    override fun validateCredentials(username: String) {
        if (TextUtils.isEmpty(username)) {
            view.errorInvalidUserName(R.string.empty_field)
            return
        }
        view.successValidUserName()
    }

    override fun fetchedUserDetails(context: ActBase, username: String): Disposable {
        return WebAPIClient.getInstance(context).getUserDetails(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    if (result != null) {
                        Prefs.putBoolean(Constants.isLoggedIn, true)
                        Prefs.putString(Constants.username, result.login)
                        val  bundle = Bundle()
                        bundle.putString(Constants.username, result.login)
                        view.openRepoListFrag(bundle)
                    }else view.onError(null)
                },
                { error -> view.onError(error.localizedMessage) }
            )
    }
}