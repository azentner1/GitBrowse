package com.demo.gitbrowse.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.gitbrowse.BuildConfig
import com.demo.gitbrowse.R
import com.demo.gitbrowse.data.model.Owner
import com.demo.gitbrowse.data.model.Repo
import com.demo.gitbrowse.ui.detail.RepoDetailFragment
import com.demo.gitbrowse.ui.main.adapter.ReposAdapter
import com.demo.gitbrowse.ui.user.UserDetailFragment
import com.demo.gitbrowse.utils.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModel()

    private val repoAdapter by lazy {
        ReposAdapter(
            onRepoSelected = {
                showRepoDetails(it)
            },
            onUserSelected = {
                showUserDetails(it)
            })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRepoObserver()
        mainActivityViewModel.fetchRepos()
        llLoadingIndicator.visibility = View.VISIBLE
        initUi()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item = menu?.findItem(R.id.menu_login)
        item?.let {
            item.isVisible = !mainActivityViewModel.isUserLoggedIn()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_login) {
            // Authorizing a user really gives him no advantages at the moment :)
            authorizeUser()
        }
        return true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val callbackUri = intent?.data
        if (callbackUri != null && callbackUri.toString().startsWith(BuildConfig.RedirectUri)) {
            val code = callbackUri.getQueryParameter("code")
            loginUser(code.toString())
            invalidateOptionsMenu()
        }
    }

    private fun initUi() {
        rvRepoList.addItemDecoration(
            DividerItemDecoration(
                ResourcesCompat.getDrawable(resources, R.drawable.recycler_view_divider, null),
                resources.getDimensionPixelOffset(R.dimen.divider_margin),
                resources.getDimensionPixelOffset(R.dimen.divider_margin)
            )
        )
        rvRepoList.layoutManager = LinearLayoutManager(this)
        rvRepoList.adapter = repoAdapter

        //Query is triggered by 4 or more characters entered
        etQuery.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length >= 4) {
                    llLoadingIndicator.visibility = View.VISIBLE
                }
                mainActivityViewModel.searchQueryChanged(s?.toString()?.toLowerCase() ?: "")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                flFragmentHolder.isVisible = false
            }
        }
    }

    private fun authorizeUser() {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "https://github.com/login/oauth/authorize?client_id="
                        + BuildConfig.ClientId + "&scope=repo&redirect_uri=" + BuildConfig.RedirectUri
            )
        )
        startActivity(intent)
    }

    private fun loginUser(code: String) {
        mainActivityViewModel.fetchToken(code).observe(this@MainActivity, Observer {
            if (it == null) {
                return@Observer
            }
            mainActivityViewModel.saveUserToken(it.accessToken)
            mainActivityViewModel.fetchRepos()
        })
    }

    private fun setRepoObserver() {
        mainActivityViewModel.reposResponse.observe(this@MainActivity, Observer {
            llLoadingIndicator.visibility = View.GONE
            if (it == null) {
                return@Observer
            }
            repoAdapter.setData(it.repos)
        })
    }

    private fun showRepoDetails(repo: Repo) {
        flFragmentHolder.isVisible = true
        supportFragmentManager.beginTransaction()
            .add(R.id.flFragmentHolder, RepoDetailFragment.newInstance(repo)).addToBackStack(null)
            .commit()
    }

    private fun showUserDetails(owner: Owner) {
        supportFragmentManager.beginTransaction()
            .add(R.id.flFragmentHolder, UserDetailFragment.newInstance(owner)).addToBackStack(null)
            .commit()
    }
}
