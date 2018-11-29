package ab.gitdemo.ui.repolist


import ab.gitdemo.ui.base.ActBase
import io.reactivex.disposables.Disposable

interface IRepoListPresenter {

    fun fetchedUserDetails(context: ActBase, username: String) : Disposable

}