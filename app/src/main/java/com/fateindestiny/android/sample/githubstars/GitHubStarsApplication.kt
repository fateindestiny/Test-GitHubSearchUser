package com.fateindestiny.android.sample.githubstars

import android.app.Application
import com.fateindestiny.android.sample.githubstars.data.local.DBHelper
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

class GitHubStarsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // DB 라이브러리 초기화
        DBHelper.initialize(this)

        // 이미지 로더 설정 초기화
        ImageLoader.getInstance().init(
            ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .build()
        )
    }
} // end of class GitHubStarsApplication