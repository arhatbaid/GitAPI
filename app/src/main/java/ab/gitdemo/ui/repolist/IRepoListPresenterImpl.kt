package ab.gitdemo.ui.repolist

import ab.gitdemo.ui.base.ActBase
import ab.gitdemo.webapi.restapi.WebAPIClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class IRepoListPresenterImpl(repoListView: IRepoListView, context: ActBase) : IRepoListPresenter {

    private var repoListView: IRepoListView? = null
    private var context: ActBase? = null

    init {
        this.repoListView = repoListView
        this.context = context
    }

    override fun fetchedUserDetails(context: ActBase, username: String): Disposable {

        return WebAPIClient.getInstance(context).getReposDetails(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> repoListView?.fetchedRepoList(result) },
                        { error -> repoListView?.onErrorFetchedRepoList(error.message.toString()) }
                )
    }

}