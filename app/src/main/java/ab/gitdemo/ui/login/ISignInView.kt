package ab.gitdemo.ui.login

import ab.gitdemo.ui.base.IBaseView
import ab.gitdemo.webapi.model.UserData

interface ISignInView : IBaseView {
    fun errorInvalidUserName(description: Int)


    fun successValidUserName()

    fun goToHomeScreen()
    fun fetchedUserDetails(result: UserData?)
    fun onErrorFetchedUserDetail(error: Throwable)
}