package com.example.practicaltest.ui.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltest.data.model.MoveListModel
import com.example.practicaltest.databinding.RawMovieListBinding
import com.example.practicaltest.utils.getImageAccordingToName


class MovieListAdapter(val onSearchHint: (movieListModel: MutableList<MoveListModel>) -> Unit) :
    ListAdapter<MoveListModel, MovieListAdapter.ViewHolder>(DiffCallback()) {
    var currentListCopyForSearch: MutableList<MoveListModel> = arrayListOf()

    inner class ViewHolder(private val binding: RawMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieListModel: MoveListModel) {
            binding.imgMovie.getImageAccordingToName(movieListModel.poster_image)
            binding.movieListModel = movieListModel
            binding.executePendingBindings()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MoveListModel>() {
        override fun areItemsTheSame(
            oldItem: MoveListModel,
            newItem: MoveListModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MoveListModel,
            newItem: MoveListModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RawMovieListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun filter(text: String) {
        var temp: MutableList<MoveListModel> = ArrayList()
        if (text.isEmpty()) {
            temp = currentListCopyForSearch
        } else {
            currentListCopyForSearch.forEach {
                if (it.name.lowercase()
                        .contains(text.lowercase()) || it.name
                        .lowercase() == text.lowercase()
                ) {
                    temp.add(it)
                }
            }
        }
        submitList(null)
        onSearchHint(temp)
        submitList(temp)
    }
}