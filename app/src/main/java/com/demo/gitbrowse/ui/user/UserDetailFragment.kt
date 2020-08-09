package com.demo.gitbrowse.ui.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.demo.gitbrowse.R
import com.demo.gitbrowse.data.model.Owner
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_details.*
import org.koin.android.viewmodel.ext.android.viewModel


class UserDetailFragment : Fragment() {

    private val userDetailViewModel: UserDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = arguments?.let {
            it.getSerializable(USER) as Owner
        } ?: throw IllegalStateException("Must be initialized here")

        userDetailViewModel.user = user

        initUi()
    }

    private fun initUi() {
        Picasso.get().load(userDetailViewModel.user.avatarUrl).into(ivUserAvatar)
        txtUserName.text = userDetailViewModel.user.login
        txtUserUrl.text = userDetailViewModel.user.url
        txtUserUrl.setOnClickListener {
            openUserUrl()
        }
    }

    private fun openUserUrl() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(userDetailViewModel.user.url)))
    }

    companion object {

        private const val USER = "user"

        fun newInstance(owner: Owner): UserDetailFragment {
            return UserDetailFragment().apply {
                arguments = bundleOf(USER to owner)
            }
        }
    }

}