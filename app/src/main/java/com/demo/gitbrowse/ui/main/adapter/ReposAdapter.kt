package com.demo.gitbrowse.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.gitbrowse.R
import com.demo.gitbrowse.data.model.Owner
import com.demo.gitbrowse.data.model.Repo
import com.google.android.material.shape.CornerFamily
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repo_list_item.view.*


class ReposAdapter(
    private var reposList: MutableList<Repo> = mutableListOf(),
    private val onRepoSelected: (repo: Repo) -> Unit,
    private val onUserSelected: (owner: Owner) -> Unit
) : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.repo_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reposList[position])
    }

    override fun getItemCount(): Int {
        return reposList.size
    }

    fun setData(reposList: List<Repo>) {
        this.reposList = reposList.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(repo: Repo) {
            itemView.txtRepoName.text = repo.name
            itemView.txtRepoIssues.text = repo.issues.toString()
            itemView.txtRepoForks.text = repo.forks.toString()
            itemView.txtRepoStarts.text =
                repo.stars.toString()
            itemView.ivAuthorThumb.shapeAppearanceModel =
                itemView.ivAuthorThumb.shapeAppearanceModel
                    .toBuilder()
                    .setAllCorners(
                        CornerFamily.ROUNDED,
                        itemView.context.resources.getDimension(R.dimen.image_corner_radius)
                    )
                    .build()
            Picasso.get().load(repo.owner.avatarUrl).into(itemView.ivAuthorThumb)
            itemView.setOnClickListener {
                onRepoSelected(repo)
            }
            itemView.ivAuthorThumb.setOnClickListener {
                onUserSelected(repo.owner)
            }
        }
    }
}