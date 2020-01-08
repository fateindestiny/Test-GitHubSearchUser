package com.fateindestiny.android.sample.githubstars.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fateindestiny.android.sample.githubstars.R
import com.fateindestiny.android.sample.githubstars.data.UserVO
import com.fateindestiny.android.sample.githubstars.view.adapter.ListRecyclerViewAdapter

class UserListFragment private constructor() : Fragment() {

    /**
     * 유저 목록에서 발생하는 이벤트 Interface.
     */
    interface EventListener {

    }


    private var listAdapter: ListRecyclerViewAdapter? = null

//    companion object {
//        fun newInstance() : UserListFragment {
//            return UserListFragment()
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.frag_user_list, container, false)


    fun showUserList(list: List<UserVO>) {
//        rvUserList.adapter = listAdapter?.apply {
//            this.userList = list
//        } ?: ListRecyclerViewAdapter(list)
    }
} // end of class UserListFragment