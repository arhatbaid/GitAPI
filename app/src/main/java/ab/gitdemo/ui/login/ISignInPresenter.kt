package ab.gitdemo.ui.login

import ab.gitdemo.ui.base.ActBase
import io.reactivex.disposables.Disposable

interface ISignInPresenter {

    fun validateCredentials(username: String)

    fun fetchedUserDetails(context: ActBase, username: String) : Disposable

}