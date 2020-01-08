package com.fateindestiny.android.sample.githubstars

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.fateindestiny.android.sample.githubstars.data.UserVO
import com.fateindestiny.android.sample.githubstars.view.adapter.ListRecyclerViewAdapter
import kotlinx.android.synthetic.main.act_main.*

class MainActivity : AppCompatActivity(), GitHubConstants.View {
    private lateinit var presenter: GitHubConstants.Presenter

    private var userListAdapter: ListRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        rvUserList.layoutManager = LinearLayoutManager(this@MainActivity)

        presenter = MainPresenter(this)

        etUserName.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when(count) {
                    0-> {
                        rvUserList.visibility = View.INVISIBLE
                    }
                    else -> {
                        if(s != null) {
                            presenter.searchUser(s.toString())
                        }
                    }
                }
            }
        })


//        presenter.searchUser("fateindestiny")
    }

    override fun showUserList(list: List<UserVO>) {
        rvUserList.adapter = userListAdapter?.apply {
            this.userList = list
        } ?: ListRecyclerViewAdapter(list)
    }
} // end of class MainActivity