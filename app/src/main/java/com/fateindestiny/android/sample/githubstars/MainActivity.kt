package com.fateindestiny.android.sample.githubstars

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
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

/**
 * 이 앱의 메인 Activity.
 * [GitHubConstants.Presenter] 를 통해 데이터를 요청하고,
 * [GitHubConstants.View]를 통해 받은 데이터를 [RecyclerView]에 적용.
 *
 * @author Ki-man, Kim
 * @since 2020-01-11
 */
class MainActivity : AppCompatActivity(), GitHubConstants.View,
    ListRecyclerViewAdapter.OnEventListener {

    private lateinit var presenter: GitHubConstants.Presenter

    // 현재 표시하고 있는 RecyclerView
    private lateinit var currentUserList: RecyclerView

    /**
     * Background Thread Hander를 만들어 api 요청시 사용.
     */
    private val handlerThread = HandlerThread("api-req-thread").apply { start() }
    private val backHandler = Handler(handlerThread.looper)

    /**
     * 데이터 변경하여 [ListRecyclerViewAdapter.notifyDataSetChanged] 호출시
     * [RecyclerView] 스크롤 처리와 충돌나는 오류가 있어 [Handler]를 활용하여 UI 갱신 처리
     */
    private val uiHandler = Handler()

    /****************************************************************************************************
     * Life Cycle
     ****************************************************************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        /**
         * API 목록 [RecyclerView] 초기화.
         * API 목록은 추가로 조회를 하기 위해 [RecyclerView.OnScrollListener]를 선언하여 해당 이벤트에서 처리.
         */
        rvUserList1.addItemDecoration(
            DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
        )
        rvUserList1.layoutManager = LinearLayoutManager(this@MainActivity)
        rvUserList1.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // 세로 스크롤을 하단으로 갈수 있는지 여부를 체크하여 스크롤 상태가 맨 마지막인지 체크.
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.searchMore()
                }
            }
        })

        /**
         * Loca 목록 [RecyclerView] 초기화.
         */
        rvUserList2.addItemDecoration(
            DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
        )
        rvUserList2.layoutManager = LinearLayoutManager(this@MainActivity)



        presenter = MainPresenter(this)
        changeUserList(presenter.getCurrentMode())

        /**
         * 검색어 입력 EditText View
         * IME의 엔터 이벤트 처리와 검색어 입력시 자동으로 검색 요청되도록 [TextWatcher] 이벤트 처리.
         */
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
                    // 기존 검색 요청한 것을 삭제.
                    backHandler.removeCallbacksAndMessages(null)
                    /**
                     * 1초의 딜레이를 주는 것은 검색을 위해 타이핑 치는 시간의 여유를 주어
                     * 너무 자주 API 요청을 보내지 않도록 하기 위함.
                     */
                    backHandler.postDelayed({
                        presenter.searchUser(s.toString())
                    }, 1000)
                }
            }
        })

        /**
         * API / Local 탭 버튼 처리.
         */
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

    /****************************************************************************************************
     * Method
     ****************************************************************************************************/
    /**
     * API, Local로 목록을 변경하는 함수.
     *
     * @param mode 목록 타입 [Mode] 값.
     */
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

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    /**
     * API 또는 Local DB를 통해 조회된 사용자 목록을 표시하는 함수.
     *
     * @param list 조회된 사용자 목록.
     */
    override fun showUserList(list: ArrayList<UserVO>) {
        val adapter = currentUserList.adapter
        if (adapter == null) {
            currentUserList.adapter = ListRecyclerViewAdapter(resources, list).apply {
                favoritListener = this@MainActivity
            }
        } else if (adapter is ListRecyclerViewAdapter) {
            adapter.userList = list
            uiHandler.post {
                currentUserList.adapter?.notifyDataSetChanged()
            }
        }
    }

    /**
     * API 목록에서 추가로 조회 요청된 데이터를 받는 함수.
     *
     * @param list 추가로 API로 조회된 사용자 목록.
     */
    override fun addUserList(list: ArrayList<UserVO>) {
        val adapter = rvUserList1.adapter
        if (adapter is ListRecyclerViewAdapter) {
            adapter.userList.addAll(list)
            uiHandler.post {
                rvUserList1.adapter?.notifyDataSetChanged()
            }
        }
    }

    /**
     * User 목록 View의 데이터 중 파라메터 데이터와 일치하는 것에 정보를 갱신하는 함수.
     *
     * @param user
     */
    override fun updateUserList(user: UserVO) {
        // API 목록의 데이터를 갱신처리.
        var adapter = rvUserList1.adapter
        if (adapter is ListRecyclerViewAdapter) {
            adapter.updateUserItem(user)
            uiHandler.post {
                rvUserList1.adapter?.notifyDataSetChanged()
            }
        }

        // Local 목록의 데이터를 갱신처리.
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

    /**
     * [ListRecyclerViewAdapter] 에서 아이템을 클릭하여 즐겨찾기 상태의 변화가 있을 때 호출되는 이벤트 함수.
     *
     * @param user 상태가 변경된 사용자 [UserVO].
     * @param isFavorit 변경된 즐겨찾기 여부.
     */
    override fun onFavoritChanged(user: UserVO, isFavorit: Boolean) {
        if (isFavorit) {
            presenter.addFavoritUser(user)
        } else {
            presenter.removeFavoritUser(user)
        }
    }
} // end of class MainActivity