package com.nyra.storyapp.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.room.Room
import com.nyra.storyapp.R
import com.nyra.storyapp.data.lokal.DatabaseStoryApp
import com.nyra.storyapp.data.lokal.entity.EntityStory
import com.nyra.storyapp.utils.ValConst.DB_NAME
import com.nyra.storyapp.utils.urlToBitmap

internal class RemoteStackViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {
    private var story: MutableList<EntityStory> = mutableListOf()

    override fun onCreate() {}

    override fun onDestroy() {}

    override fun onDataSetChanged() {
        val dbs = Room.databaseBuilder(
            context.applicationContext, DatabaseStoryApp::class.java, DB_NAME
        ).build()
        dbs.getDaoStory().getAllStory().forEach {
            story.add(
                EntityStory(
                    it.id,
                    it.photoUrl
                )
            )
        }
    }

    override fun getCount(): Int = story.size
    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.item_story_widget)
        remoteViews.setImageViewBitmap(R.id.storyImg, urlToBitmap(story[position].photoUrl))

        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}