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

/**
 * 사용자 목록을 표시하기 위한 [RecyclerView.Adapter] Class.
 * [UserVO] 데이터에 따라 정보를 표시.
 *
 * 기능
 * 1. 사용자 목록 표시.
 * 2. 사용자 아이템 클릭시 이벤트 호출.
 *
 * @author Ki-man, Kim
 * @since 2020-01-11
 */
class ListRecyclerViewAdapter(res: Resources, var userList: ArrayList<UserVO>) :
    RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder>(),
    View.OnClickListener {

    /**
     * [ImageLoader] 라이브러리의 로딩 옵션 객체.
     * 이미지의 라운딩 처리를 위해 특정 옵션을 추가.
     */
    private val imageLoaderOption = DisplayImageOptions.Builder()
        .displayer(RoundedBitmapDisplayer(res.getDimensionPixelSize(R.dimen.avatar_size))) // 이미지 라운딩 효과 추가.
        .build()

    /**
     * 목록의 이벤트 처리 객체.
     */
    var favoritListener: OnEventListener? = null

    /**
     * 아이템 클릭 이벤트 리스너.
     */
    interface OnEventListener {
        /**
         * 아이템을 클릭하여 사용자 즐겨찾기 정보 변경 이벤트 함수.
         *
         * @param user 변경된 사용자 [UserVO]
         * @param isFavorit 변경된 즐겨찾기 여부 값.
         */
        fun onFavoritChanged(user: UserVO, isFavorit: Boolean)
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
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

            // 초성 그룹 표시 여부.
            if (position > 0 && item.initialChars == userList[position - 1].initialChars) {
                // 해당 아이템의 포지션이 첫번째가 아니고 바로 이전 아이템의 초성과 같다면
                // 초성 View를 표시하지 않음.
                txtInitialChar.visibility = View.GONE
            } else {
                // 해당 아이템의 초성의 첫번째 아이템이므로 초성 View를 표시.
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

    /****************************************************************************************************
     * Event
     ****************************************************************************************************/
    override fun onClick(v: View?) {
        val user = v?.tag ?: return
        if (user is UserVO) {
            favoritListener?.onFavoritChanged(user, !user.isFavorit)
        }
    }

    /****************************************************************************************************
     * Method
     ****************************************************************************************************/
    /**
     * 사용자 정보를 갱신하는 함수.
     *
     * @param user 정보를 갱신할 사용자 정보 [UserVO]
     */
    fun updateUserItem(user: UserVO) {
        val idx = userList.indexOf(user)
        if (idx > -1) {
            userList[idx].isFavorit = user.isFavorit
        }
    }

    /**
     * 사용자 아이템을 삭제하는 함수.
     *
     * @param user 삭제할 사용자 정보 [UserVO]
     */
    fun removeUserItem(user: UserVO) {
        userList.remove(user)
        notifyDataSetChanged()
    }
    /****************************************************************************************************
     * Inner Class
     ****************************************************************************************************/
    /**
     * [RecyclerView.Adapter]의 [RecyclerView.ViewHolder] Class.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // 초성 View.
        val txtInitialChar: TextView = view.txtInitialChar

        // 사용자 아바타 이미지 View.
        val ivAvatar: ImageView = view.ivAvatar

        // 사용자 이름 View.
        val txtUserName: TextView = view.txtUserName

        // 즐겨찾기 여부 View.
        val ivFavorit: ImageView = view.ivFavorit
    } // end of class ViewHolder
} // end of class ListRecyclerViewAdapter