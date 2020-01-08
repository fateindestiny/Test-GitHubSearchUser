package com.fateindestiny.android.sample.githubstars.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fateindestiny.android.sample.githubstars.R
import com.fateindestiny.android.sample.githubstars.data.UserVO
import kotlinx.android.synthetic.main.item_user_list.view.*

class ListRecyclerViewAdapter(var userList: List<UserVO>) :
    RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_user_list,
            parent,
            false
        )
    )


    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        userList[position].let {
            holder.txtUserName.text = it.login
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtUserName: TextView = view.txtUserName
    } // end of class ViewHolder
} // end of class ListRecyclerViewAdapter