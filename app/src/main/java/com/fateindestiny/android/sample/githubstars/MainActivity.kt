package com.fateindestiny.android.sample.githubstars

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fateindestiny.android.sample.githubstars.data.UserVO
import com.fateindestiny.android.sample.githubstars.view.adapter.ListRecyclerViewAdapter
import kotlinx.android.synthetic.main.act_main.*

class MainActivity : AppCompatActivity(), GitHubConstants.View, ListRecyclerViewAdapter.OnEventListener{
    private lateinit var presenter: GitHubConstants.Presenter

    private var userListAdapter: ListRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        rvUserList.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        rvUserList.layoutManager = LinearLayoutManager(this@MainActivity)

        presenter = MainPresenter(this)

        etUserName.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    presenter.searchUser(v.text.toString())
                }
                else ->
                    presenter.searchUser(v.text.toString())
            }
            true
        }

        etUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when (count) {
                    0 -> {
                        rvUserList.visibility = View.INVISIBLE
                    }
                    else -> {
                        if (s != null) {
                            presenter.searchUser(s.toString())
                        }
                    }
                }
            }
        })
    }

    override fun showUserList(list: List<UserVO>) {
        rvUserList.adapter = userListAdapter?.apply {
            this.userList = list
        } ?: ListRecyclerViewAdapter(resources, list).apply { this.favoritListener = this@MainActivity }
    }

    override fun OnFavoritChanged(user: UserVO, isFavorit: Boolean) {
        if(isFavorit) {
            presenter.addFavoritUser(user)
        }
    }
} // end of class MainActivity