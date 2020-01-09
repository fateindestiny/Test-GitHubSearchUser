package com.fateindestiny.android.sample.githubstars

import android.app.Application
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

class GitHubStarsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ImageLoader.getInstance().init(
            ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .build()
        )
    }
} // end of class GitHubStarsApplication