package com.fateindestiny.android.sample.githubstars.data.local

import com.fateindestiny.android.sample.githubstars.data.UserVO
import com.fateindestiny.tagdbfactory.TagDBFactory
import java.sql.SQLException

object DBHelper : TagDBFactory() {

    private val dbInfo = DBInfo("favorit.db", 1)

    fun insertFavoritUser(user: UserVO, tableName: String) {
        try {
            val db = open(dbInfo)
            db.insert(tableName, null, user.getContentValues())
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }
} // end of class DBHelper