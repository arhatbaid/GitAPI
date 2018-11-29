package ab.gitdemo.ui.repolist

import ab.gitdemo.ui.base.IBaseView
import ab.gitdemo.webapi.model.RepoData

interface IRepoListView : IBaseView {

    fun fetchedRepoList(result: ArrayList<RepoData>?)

    fun onErrorFetchedRepoList(error: String)
}