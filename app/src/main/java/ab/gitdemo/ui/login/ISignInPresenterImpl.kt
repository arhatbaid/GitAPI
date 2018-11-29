package ab.gitdemo.ui.login

import ab.gitdemo.R
import ab.gitdemo.ui.base.ActBase
import ab.gitdemo.webapi.restapi.WebAPIClient
import android.text.TextUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ISignInPresenterImpl(signInView: ISignInView, context: ActBase) : ISignInPresenter {

    private var signInView: ISignInView? = null
    private var context: ActBase? = null

    init {
        this.signInView = signInView
        this.context = context
    }

    override fun validateCredentials(username: String) {
        if (signInView != null) {
            if (TextUtils.isEmpty(username)) {
                signInView?.errorInvalidUserName(R.string.empty_field)
                return
            }
            signInView?.successValidUserName()
        }
    }

    override fun fetchedUserDetails(context: ActBase, username: String): Disposable {

        return WebAPIClient.getInstance(context).getUserDetails(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> signInView?.fetchedUserDetails(result) },
                        { error -> signInView?.onErrorFetchedUserDetail(error) }
                )
    }

}