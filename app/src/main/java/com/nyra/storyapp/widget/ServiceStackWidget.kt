package com.nyra.storyapp.widget

import android.content.Intent
import android.widget.RemoteViewsService

class ServiceStackWidget: RemoteViewsService() {
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory =
        RemoteStackViewsFactory(this.applicationContext)

}