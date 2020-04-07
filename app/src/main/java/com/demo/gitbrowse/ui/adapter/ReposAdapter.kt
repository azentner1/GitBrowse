package com.demo.gitbrowse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.gitbrowse.R
import com.demo.gitbrowse.data.model.Repo
import kotlinx.android.synthetic.main.repo_list_item.view.*
import java.text.MessageFormat


class ReposAdapter(private var reposList: MutableList<Repo> = mutableListOf()) : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_list_item, parent, false)
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
            itemView.txtRepoStarts.text = MessageFormat.format("{0}: {1}", itemView.context.getString(R.string.num_of_stars), repo.stars)
        }
    }
}