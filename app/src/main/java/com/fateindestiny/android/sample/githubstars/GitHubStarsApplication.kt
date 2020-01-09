package com.fateindestiny.android.sample.githubstars

import android.app.Application
import com.fateindestiny.android.sample.githubstars.data.local.DBHelper
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

class GitHubStarsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DBHelper.initialize(this)

        ImageLoader.getInstance().init(
            ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .build()
        )
    }
} // end of class GitHubStarsApplication