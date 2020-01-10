package com.fateindestiny.android.sample.githubstars.view.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fateindestiny.android.sample.githubstars.R
import com.fateindestiny.android.sample.githubstars.data.UserVO
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import kotlinx.android.synthetic.main.item_user_list.view.*

class ListRecyclerViewAdapter(res: Resources, var userList: ArrayList<UserVO>) :
    RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder>(),
    View.OnClickListener {

    private val imageLoaderOption = DisplayImageOptions.Builder()
        .displayer(RoundedBitmapDisplayer(res.getDimensionPixelSize(R.dimen.avatar_size)))
        .build()

    var favoritListener: OnEventListener? = null

    interface OnEventListener {
        fun onFavoritChanged(user: UserVO, isFavorit: Boolean)
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
        val item = userList[position]
        holder.run {

            if (position > 0 && item.initialChars == userList[position - 1].initialChars) {
                txtInitialChar.visibility = View.GONE
            } else {
                txtInitialChar.text = item.initialChars
                txtInitialChar.visibility = View.VISIBLE
            }
            // 스크롤시 기존 이미지가 표시되어 오류와 같이 보이기 때문에 기존 이미지삭제.
            ivAvatar.setImageBitmap(null)
            ImageLoader.getInstance()
                .displayImage(item.avatarUrl, ivAvatar, imageLoaderOption)
            txtUserName.text = item.login
            ivFavorit.isEnabled = item.isFavorit
            itemView.tag = item
            itemView.setOnClickListener(this@ListRecyclerViewAdapter)
        }
    }

    override fun onClick(v: View?) {
        val user = v?.tag ?: return
        if (user is UserVO) {
            favoritListener?.onFavoritChanged(user, !user.isFavorit)
        }
    }

    fun updateUserItem(user: UserVO) {
        val idx = userList.indexOf(user)
        if (idx > -1) {
            userList[idx].isFavorit = user.isFavorit
        }
    }

    fun removeUserItem(user: UserVO) {
        userList.remove(user)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtInitialChar: TextView = view.txtInitialChar
        val ivAvatar: ImageView = view.ivAvatar
        val txtUserName: TextView = view.txtUserName
        val ivFavorit: ImageView = view.ivFavorit
    } // end of class ViewHolder
} // end of class ListRecyclerViewAdapter