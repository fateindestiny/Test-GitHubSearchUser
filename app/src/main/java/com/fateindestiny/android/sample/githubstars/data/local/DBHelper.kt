package com.fateindestiny.android.sample.githubstars.data.local

import android.database.Cursor
import com.fateindestiny.android.sample.githubstars.data.UserVO
import com.fateindestiny.android.sample.githubstars.util.Util
import com.fateindestiny.tagdbfactory.TagDBFactory
import java.sql.SQLException

object DBHelper : TagDBFactory() {

    private val dbInfo = DBInfo("/sdcard/Download/favorit.db", 1)
    private val db by lazy { open(dbInfo) }

    fun selectFavoritUser(userName: String): ArrayList<UserVO> {
        val list: ArrayList<UserVO> = arrayListOf()
        try {
            val cursor = db.query(
                DBInfo.TBL_FAVORIT_USER.NAME, null,
                // userName이 없으면 모든 조건 없이, userName이 있으면 해당 이름 값으로 조회.
                if (userName.isEmpty()) null else "${DBInfo.TBL_FAVORIT_USER.LOGIN} like %$userName%",
                null, null, null, "${DBInfo.TBL_FAVORIT_USER.LOGIN} ASC"
            )
            if (cursor.moveToFirst()) {
                do {
                    list.add(
                        UserVO(
                            getStringByColumnName(cursor, DBInfo.TBL_FAVORIT_USER.LOGIN),
                            getStringByColumnName(cursor, DBInfo.TBL_FAVORIT_USER.AVATAR_URL)
                        ).apply {
                            isFavorit = true
                            initialChars = Util.getInitialChar(login)
                        }
                    )
                } while (cursor.moveToNext())
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return list
    }

    fun hasFavoritUser(userName: String): Boolean {
        var result = false
        try {
            val cursor = db.query(
                DBInfo.TBL_FAVORIT_USER.NAME, null,
                "${DBInfo.TBL_FAVORIT_USER.LOGIN}=\"$userName\"",
                null, null, null, null
            )
            result = cursor.count > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return result
    }

    fun insertFavoritUser(user: UserVO): Long {
        var result = 0L
        try {
            result = db.insert(DBInfo.TBL_FAVORIT_USER.NAME, null, user.getContentValues())
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return result
    }

    fun deleteFavoritUser(user: UserVO): Int {
        var result = 0
        try {
            result = db.delete(
                DBInfo.TBL_FAVORIT_USER.NAME,
                "${DBInfo.TBL_FAVORIT_USER.LOGIN}=\"${user.login}\"",
                null
            )
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return result
    }

    private fun getStringByColumnName(cursor: Cursor, name: String): String =
        when (val idx = cursor.getColumnIndex(name)) {
            -1 -> ""
            else -> cursor.getString(idx)
        }

} // end of class DBHelper