package com.arhatbaid.boxmedemo.ui.repodetail

import ab.gitdemo.R
import ab.gitdemo.ui.base.FragBase
import ab.gitdemo.utils.Constants
import ab.gitdemo.webapi.model.RepoData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.thefinestartist.finestwebview.FinestWebView
import kotlinx.android.synthetic.main.frag_repo_detail.*

class FragRepoDetail : FragBase(),
        View.OnClickListener {

    private var repoData: RepoData? = null

    override fun onClick(v: View?) {
        when (v) {
            btnBrowser -> {
                FinestWebView.Builder(getActBase())
                        .iconDefaultColorRes(R.color.finestWhite)
                        .show(repoData?.htmlUrl!!)
//                FinestWebView.Builder(getActBase()).show(repoData?.htmlUrl!!)
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_repo_detail, container, false)
    }

    override fun setUpView(view: View) {


        toolbar.setNavigationOnClickListener {
            getActBase().supportFragmentManager.popBackStack()
        }
        repoData = arguments?.getParcelable(Constants.repo_detail) as RepoData
        Picasso.get().load(repoData?.owner?.avatarUrl).into(imgUser)
        lblRepoTitle.text = repoData?.name
        lblRepoDesc.text = repoData?.description
        lblStarCount.text = repoData?.stargazersCount.toString()
        lblWatchCount.text = repoData?.watchersCount.toString()
        lblForkCount.text = repoData?.forksCount.toString()
        btnBrowser.setOnClickListener(this)
    }
}