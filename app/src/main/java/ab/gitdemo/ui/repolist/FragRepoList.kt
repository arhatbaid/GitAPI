package ab.gitdemo.ui.repolist

import ab.gitdemo.R
import ab.gitdemo.ui.base.FragBase
import ab.gitdemo.utils.Constants
import ab.gitdemo.webapi.model.RepoData
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ab.gitdemo.ui.repodetail.FragRepoDetail
import com.arhatbaid.boxmedemo.ui.startup.FragStartUp
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.frag_repo_list.*


class FragRepoList : FragBase(),
        View.OnClickListener,
        IRepoListView,
        AdapterRepoList.OnItemClickListener {

        private var iRepoListPresenterImpl: IRepoListPresenterImpl? = null

    private var mAdapterRepoList: AdapterRepoList? = null

    private var arrRepo = ArrayList<RepoData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_repo_list, container, false)
    }

    override fun setUpView(view: View) {
        var username = arguments?.getString(Constants.username)

        if (username.isNullOrEmpty()) {
            username = Prefs.getString(Constants.username, "")
        }

        iRepoListPresenterImpl = IRepoListPresenterImpl(this, getActBase())
        if (!username.isNullOrEmpty()) {
            iRepoListPresenterImpl?.fetchedUserDetails(getActBase(), username!!)

            rcvRepo?.layoutManager = LinearLayoutManager(getActBase())

            mAdapterRepoList = AdapterRepoList(getActBase(), arrRepo, this)
            rcvRepo?.adapter = mAdapterRepoList
        }

        floationgButtonVisibilityBehaviour()

        fabLogout.setOnClickListener(this)

//        clearBackstack()
    }


    override fun onClick(v: View?) {
        when (v) {
            this.fabLogout -> {
                Prefs.clear()
                clearBackstack()
                initFrag(FragStartUp())
            }
        }
    }

    override fun fetchedRepoList(result: ArrayList<RepoData>?) {
        arrRepo.clear()
        if (result != null && result.isNotEmpty()) {
            arrRepo.addAll(result)
            mAdapterRepoList?.notifyDataSetChanged()
            rcvRepo.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            lblNoRepoFound.visibility = View.GONE
        } else {
            rcvRepo.visibility = View.GONE
            progressBar.visibility = View.GONE
            lblNoRepoFound.visibility = View.VISIBLE
        }
    }

    override fun onErrorFetchedRepoList(error: String) {
        onError(error)
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.repo_detail, arrRepo[position])
        addFragment(FragRepoDetail(), bundle)
    }

    private fun floationgButtonVisibilityBehaviour() {
        rcvRepo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                try {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0 && fabLogout.visibility == View.VISIBLE) {
                        fabLogout.hide()
                    } else if (dy < 0 && fabLogout.visibility !== View.VISIBLE) {
                        fabLogout.show()
                    }
                } catch (e: Exception) {
                }
            }
        })

    }
}