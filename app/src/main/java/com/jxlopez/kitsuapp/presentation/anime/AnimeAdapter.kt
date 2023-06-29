package com.jxlopez.kitsuapp.presentation.anime

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jxlopez.kitsuapp.databinding.AnimeCardItemBinding
import com.jxlopez.kitsuapp.model.AnimeItem
import com.jxlopez.kitsuapp.utils.extensions.loadImageUrl

class AnimeAdapter
    : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {
    lateinit var onItemClick: (AnimeItem) -> Unit

    private val diffCallback = object : DiffUtil.ItemCallback<AnimeItem>(){
        override fun areItemsTheSame(oldItem: AnimeItem, newItem: AnimeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AnimeItem, newItem: AnimeItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitList(list: List<AnimeItem>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = AnimeCardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    fun setOnItemClickListener(block: (AnimeItem) -> Unit) {
        onItemClick = block
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        with(holder){
            with(differ.currentList[position]) {
                binding.tvTitle.text = title
                binding.tvReleased.text = releaseDate
                binding.ivImage.loadImageUrl(posterImage)

                binding.bgContent.setOnClickListener {
                    if (::onItemClick.isInitialized)
                        onItemClick(this)
                }
            }
        }
    }

    inner class AnimeViewHolder(val binding: AnimeCardItemBinding)
        : RecyclerView.ViewHolder(binding.root)

}