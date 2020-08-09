package com.demo.gitbrowse.ui.detail

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.demo.gitbrowse.R
import com.demo.gitbrowse.data.model.Repo
import kotlinx.android.synthetic.main.fragment_repo_details.*
import org.koin.android.viewmodel.ext.android.viewModel


class RepoDetailFragment : Fragment() {

    private val repoDetailViewModel: RepoDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repo = arguments?.let {
            it.getSerializable(REPO) as Repo
        } ?: throw IllegalStateException("Must be initialized here")

        repoDetailViewModel.repo = repo
        initUi()
    }


    private fun initUi() {
        txtDetailsRepoName.text = repoDetailViewModel.repo.name
        if(repoDetailViewModel.repo.private) {
            txtDetailsPublic.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.red)))
            txtDetailsPublic.text = getString(R.string.private_text)
        } else {
            txtDetailsPublic.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.green)))
            txtDetailsPublic.text = getString(R.string.public_text)
        }
        txtDetailsLanguage.text = repoDetailViewModel.repo.language
        txtDetailsUrl.text = repoDetailViewModel.repo.url
        txtDetailsUrl.setOnClickListener {
            openRepoUrl()
        }
        txtDetailsStars.text = repoDetailViewModel.repo.stars.toString()
        txtDetailsForks.text = repoDetailViewModel.repo.forks.toString()
        txtDetailsIssues.text = repoDetailViewModel.repo.issues.toString()
        txtDetailsOwner.text = repoDetailViewModel.repo.owner.login
    }

    private fun openRepoUrl() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(repoDetailViewModel.repo.url)))
    }

    companion object {

        private const val REPO = "repo"

        fun newInstance(repo: Repo): RepoDetailFragment {
            return RepoDetailFragment().apply {
                arguments = bundleOf(REPO to repo)
            }
        }
    }
}