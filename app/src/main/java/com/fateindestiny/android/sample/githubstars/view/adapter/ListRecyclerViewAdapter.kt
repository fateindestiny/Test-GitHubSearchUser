package com.fateindestiny.android.sample.githubstars.view.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fateindestiny.android.sample.githubstars.R
import com.fateindestiny.android.sample.githubstars.data.UserVO
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import kotlinx.android.synthetic.main.item_user_list.view.*

class ListRecyclerViewAdapter(res: Resources, var userList: List<UserVO>) :
    RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder>(),
    CompoundButton.OnCheckedChangeListener {

    private val imageLoaderOption = DisplayImageOptions.Builder()
        .displayer(RoundedBitmapDisplayer(res.getDimensionPixelSize(R.dimen.avatar_size)))
        .build()

    var favoritListener: OnEventListener? = null

    interface OnEventListener {
        fun OnFavoritChanged(user: UserVO, isFavorit: Boolean)
    }

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
            holder.run {
                // 스크롤시 기존 이미지가 표시되어 오류와 같이 보이기 때문에 기존 이미지삭제.
                ivAvatar.setImageBitmap(null)
                ImageLoader.getInstance()
                    .displayImage(it.avatarUrl, ivAvatar, imageLoaderOption)
                txtUserName.text = it.login
                chkFavorit.isChecked = it.isFavorit
                chkFavorit.tag = it
                chkFavorit.setOnCheckedChangeListener(this@ListRecyclerViewAdapter)
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val user = buttonView?.tag ?: return
        if (user is UserVO) {
            favoritListener?.OnFavoritChanged(user, isChecked)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar: ImageView = view.ivAvatar
        val txtUserName: TextView = view.txtUserName
        val chkFavorit: CheckBox = view.chkFavorit
    } // end of class ViewHolder
} // end of class ListRecyclerViewAdapter