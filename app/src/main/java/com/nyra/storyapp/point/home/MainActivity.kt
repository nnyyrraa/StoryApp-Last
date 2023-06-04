package com.nyra.storyapp.point.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.nyra.storyapp.R.string
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyra.storyapp.R
import com.nyra.storyapp.data.remot.ApiResponse
import com.nyra.storyapp.data.remot.story.ServiceStory
import com.nyra.storyapp.data.source.DataSourceStory
import com.nyra.storyapp.databinding.ActivityMainBinding
import com.nyra.storyapp.point.maps.MapsActivity
import com.nyra.storyapp.point.profil.ProfileActivity
import com.nyra.storyapp.point.story.AdapterStory
import com.nyra.storyapp.point.story.StoryViewModel
import com.nyra.storyapp.point.story.add.StoryAddActivity
import com.nyra.storyapp.utils.ManagerSession
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModelStory: StoryViewModel by viewModels()

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding!!

    private lateinit var pref: ManagerSession
    private var token: String? = null

    private lateinit var adapter: AdapterStory

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_activityMainBinding?.root)
        pref = ManagerSession(this)
        token = pref.getToken

        actionInit()
        uiInit()

        getStory()
    }

    private fun getStory() {
        val adapter = AdapterStory()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        viewModelStory.story.observe(this) {
            isLoading(true)
            if (it != null) {
                isLoading(false)
                adapter.submitData(lifecycle, it)
            }
        }
    }

    private fun uiInit() {
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        binding.tvHaloName.text = getString(string.label_user, pref.getUsername)
    }

    private fun actionInit() {
        binding.btnAddStory.setOnClickListener {
            StoryAddActivity.start(this)
        }
        binding.btnProfile.setOnClickListener {
            ProfileActivity.start(this)
        }
    }

    private fun isLoading(loading: Boolean) {
    if (loading) {
        binding.apply {
            loadingRefresh.visibility = View.VISIBLE
            loadingRefresh.startShimmer()
            rvStory.visibility = View.INVISIBLE
        }
    } else {
        binding.apply {
            rvStory.visibility = View.VISIBLE
            loadingRefresh.stopShimmer()
            loadingRefresh.visibility = View.INVISIBLE
        }
    }
    }

    override fun onResume() {
        super.onResume()
        getStory()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingMenu -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.mapAction -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}