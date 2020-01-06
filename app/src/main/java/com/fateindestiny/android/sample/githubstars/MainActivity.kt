package com.fateindestiny.android.sample.githubstars

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fateindestiny.android.sample.githubstars.data.UserVO

class MainActivity : AppCompatActivity(), GitHubConstants.View {
    private lateinit var presenter: GitHubConstants.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        presenter = MainPresenter(this)
        presenter.searchUser("fateindestiny")
    }

    override fun showUserList(list: List<UserVO>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
} // end of class MainActivity