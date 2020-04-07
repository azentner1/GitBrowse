package com.demo.gitbrowse.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.gitbrowse.BuildConfig
import com.demo.gitbrowse.R
import com.demo.gitbrowse.ui.adapter.ReposAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModel()

    private val repoAdapter by lazy {
        ReposAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(!mainActivityViewModel.isUserLoggedIn()) {
            authorizeUser()
        } else {
            fetchRepos(mainActivityViewModel.getUserAccessToken())
        }
        initUi()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val callbackUri = intent?.data
        if(callbackUri != null && callbackUri.toString().startsWith(BuildConfig.RedirectUri)) {
            val code = callbackUri.getQueryParameter("code")
            loginUser(code.toString())
        }
    }

    private fun initUi() {
        rvRepoList.layoutManager = LinearLayoutManager(this)
        rvRepoList.adapter = repoAdapter
    }

    private fun authorizeUser() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/oauth/authorize?client_id="
                + BuildConfig.ClientId + "&scope=repo&redirect_uri=" + BuildConfig.RedirectUri
        ))
        startActivity(intent)
    }

    private fun loginUser(code: String) {
        mainActivityViewModel.fetchToken(code).observe(this@MainActivity, Observer {
            if (it == null) {
                return@Observer
            }
            mainActivityViewModel.saveUserToken(it.accessToken)
            fetchRepos(it.accessToken)
        })
    }

    private fun fetchRepos(accessToken: String) {
        mainActivityViewModel.fetchRepos(accessToken).observe(this@MainActivity, Observer {
            if (it == null) {
                return@Observer
            }
            repoAdapter.setData(it.repos)
        })
    }
}
