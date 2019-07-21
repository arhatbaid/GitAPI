package com.arhatbaid.boxmedemo.ui.startup

import ab.gitdemo.R
import ab.gitdemo.ui.base.FragBase
import ab.gitdemo.ui.login.FragLogin
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.frag_startup.*

class FragStartUp : FragBase(),
        View.OnClickListener {

    private val arrTrans = HashMap<String, View>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_startup, container, false)
    }

    override fun setUpView(view: View) {
        btnSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnSignUp -> {
                arrTrans[getString(R.string.tran_sign_up)] = btnSignUp as View
                replaceFragment(FragLogin(), null, arrTrans)
            }
        }
    }
}