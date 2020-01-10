package com.fateindestiny.android.sample.githubstars

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fateindestiny.android.sample.githubstars.data.UserVO
import com.fateindestiny.android.sample.githubstars.presenter.GitHubConstants
import com.fateindestiny.android.sample.githubstars.presenter.MainPresenter
import com.fateindestiny.android.sample.githubstars.presenter.Mode
import com.fateindestiny.android.sample.githubstars.view.adapter.ListRecyclerViewAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.act_main.*

class MainActivity : AppCompatActivity(), GitHubConstants.View,
    ListRecyclerViewAdapter.OnEventListener {
    private lateinit var presenter: GitHubConstants.Presenter

    private var userListAdapter: ListRecyclerViewAdapter? = null
    private lateinit var currentUserList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        rvUserList1.addItemDecoration(
            DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
        )
        rvUserList1.layoutManager = LinearLayoutManager(this@MainActivity)

        rvUserList2.addItemDecoration(
            DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
        )
        rvUserList2.layoutManager = LinearLayoutManager(this@MainActivity)



        presenter = MainPresenter(this)
        changeUserList(presenter.getCurrentMode())

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
                if (s != null) {
                    presenter.searchUser(s.toString())
                }
            }
        })

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    getString(R.string.api) -> {
                        // API 탭이 선택되었을 경우.
                        presenter.changeMode(Mode.API)
                    }
                    getString(R.string.local) -> {
                        // Local 탭이 선택되었을 경우.
                        presenter.changeMode(Mode.LOCAL)
                    }
                }
                changeUserList(presenter.getCurrentMode())
                presenter.searchUser("")
            }
        })
    }

    fun changeUserList(mode: Mode) {
        when (mode) {
            Mode.API -> {
                rvUserList1.visibility = View.VISIBLE
                rvUserList2.visibility = View.GONE
                currentUserList = rvUserList1
            }
            Mode.LOCAL -> {
                rvUserList1.visibility = View.GONE
                rvUserList2.visibility = View.VISIBLE
                currentUserList = rvUserList2
            }
        }
    }

    override fun showUserList(list: ArrayList<UserVO>) {
        currentUserList.adapter = userListAdapter?.apply {
            this.userList = list
        } ?: ListRecyclerViewAdapter(resources, list).apply {
            this.favoritListener = this@MainActivity
        }
    }

    /**
     * User 목록 View의 데이터 중 파라메터 데이터와 일치하는 것에 정보를 갱신하는 함수.
     *
     * @param user
     */
    override fun updateUserList(user: UserVO) {
        var adapter = rvUserList1.adapter

        if (adapter is ListRecyclerViewAdapter) {
            adapter.updateUserItem(user)
        }
        adapter = rvUserList2.adapter
        if (adapter is ListRecyclerViewAdapter) {
            adapter.updateUserItem(user)
            // Local은 해당 즐겨찾기 해제시 목록에서 삭제되어야 하기에 사용자 즐겨찾기 여부에 따라 분기처리.
            if (!user.isFavorit) {
                adapter.removeUserItem(user)
            } else {
                adapter.updateUserItem(user)
            }

        }
    }

    override fun OnFavoritChanged(user: UserVO, isFavorit: Boolean) {
        if (isFavorit) {
            presenter.addFavoritUser(user)
        } else {
            presenter.removeFavoritUser(user)
        }
    }
} // end of class MainActivity