package com.nyra.storyapp.point.story.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nyra.storyapp.R.string
import com.nyra.storyapp.data.model.DetailStory
import com.nyra.storyapp.databinding.ActivityStoryDetailBinding
import com.nyra.storyapp.utils.ValConst
import com.nyra.storyapp.utils.ext.setImageUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryDetailActivity : AppCompatActivity() {
    private var _activityStoryDetailBinding: ActivityStoryDetailBinding? = null
    private val binding get() = _activityStoryDetailBinding!!

    private lateinit var stories: DetailStory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityStoryDetailBinding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(_activityStoryDetailBinding?.root)

        intentInit()
        uiInit()
    }

    private fun intentInit() {
        stories = intent.getParcelableExtra(ValConst.BUNDLE_KEY_STORE)!!
    }

    private fun uiInit() {
        binding.apply {
            tvStoryUser.text = stories.name
            thumbnailImgStory.setImageUrl(stories.photoUrl, true)
            tvDescStory.text = stories.description
        }
        title = getString(string.story_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
    }
}