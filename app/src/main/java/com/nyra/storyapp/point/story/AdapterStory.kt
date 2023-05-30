package com.nyra.storyapp.point.story

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nyra.storyapp.point.story.detail.StoryDetailActivity
import com.nyra.storyapp.data.model.DetailStory
import com.nyra.storyapp.databinding.ItemStoryBinding
import com.nyra.storyapp.utils.ValConst
import com.nyra.storyapp.utils.ext.setImageUrl
import com.nyra.storyapp.utils.ext.timeStamptoString

class AdapterStory(/*private val listStory: List<DetailStory>*/) : PagingDataAdapter<DetailStory, AdapterStory.StoryViewHolder>(DiffCallback) {
    private lateinit var callbackItem: OnItemClickCallback
    private val data = ArrayList<DetailStory>()

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<DetailStory>() {
            override fun areItemsTheSame(oldItem: DetailStory, newItem: DetailStory): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DetailStory, newItem: DetailStory): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterStory.StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterStory.StoryViewHolder, position: Int) {
        /*listStory[position].let { story ->
            holder.bind(story)
        }*/

        val story = getItem(position)
        if(story != null){
            holder.bind(story)
            holder.itemView.setOnClickListener {
                callbackItem.onItemClicked(story)
            }
        }
    }

    //override fun getItemCount() = data.size

    inner class StoryViewHolder(private val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(stories: DetailStory) {
            with(binding) {
                nameStory.text = stories.name
                tvDescStory.text = stories.description
                tvDateStory.text = stories.createdAt.timeStamptoString()

                imageStory.setImageUrl(stories.photoUrl, true)
            }
            itemView.setOnClickListener {
                val intent = Intent(it.context, StoryDetailActivity::class.java)
                intent.putExtra(ValConst.BUNDLE_KEY_STORE, stories)

                val compatOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(binding.imageStory, "thumbnail"),
                    Pair(binding.nameStory, "title"),
                    Pair(binding.tvDescStory, "description"),
                )
                itemView.context.startActivity(intent, compatOptions.toBundle())
            }
            /*binding.apply {
                Glide.with(this.root.context)
                    .load(stories.photoUrl)
                    .centerCrop()
                    .into(imageStory)
                nameStory.text = stories.name
            }*/
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(story: DetailStory)
    }

    fun clickCallback(onItemClickCallback: OnItemClickCallback){
        this.callbackItem = onItemClickCallback
    }
}