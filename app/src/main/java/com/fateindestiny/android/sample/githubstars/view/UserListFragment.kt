package com.fateindestiny.android.sample.githubstars.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class UserListFragment private constructor() : Fragment() {

    /**
     * 유저 목록에서 발생하는 이벤트 Interface.
     */
    interface EventListener {

    }

    companion object {
        fun newInstance() : UserListFragment {
            return UserListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
} // end of class UserListFragment