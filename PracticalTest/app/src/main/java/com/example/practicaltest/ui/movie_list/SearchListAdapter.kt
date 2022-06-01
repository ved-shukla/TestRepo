package com.example.practicaltest.ui.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltest.data.model.MoveListModel
import com.example.practicaltest.databinding.RawSearchListBinding


class SearchListAdapter(val onClick: (movieListModel: MoveListModel) -> Unit) :
    ListAdapter<MoveListModel, SearchListAdapter.ViewHolder>(MovieListAdapter.DiffCallback()) {

    inner class ViewHolder(private val binding: RawSearchListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieListModel: MoveListModel) {
            binding.root.setOnClickListener {
                onClick.invoke(movieListModel)
            }
            binding.movieListModel = movieListModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RawSearchListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}