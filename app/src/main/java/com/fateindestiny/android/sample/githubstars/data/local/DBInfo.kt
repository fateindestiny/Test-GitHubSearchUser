package com.fateindestiny.android.sample.githubstars.data.local

import com.fateindestiny.tagdbfactory.*

class DBInfo(dbFileName:String, dbVersion:Int) : TagDBInfo(dbFileName, dbVersion) {

    @TagTable
    class TBL_FAVORIT_USER {
       companion object {
           @TagTableName
           const val NAME = "tbl_favorit_user"

           @TagColumn(type = TagDBFactory.COLUMN_TYPE.INTEGER, isPrimaryKey = true, isAutoIncrement = true)
           const val IDX = "idx"

           @TagColumn(type = TagDBFactory.COLUMN_TYPE.TEXT)
           const val LOGIN:String = "login"

           @TagColumn(type = TagDBFactory.COLUMN_TYPE.TEXT)
           const val AVATAR_URL = "avatar_url"
       }

    } // end of class TBL_FAVORIT_USER
} // end of class DBInfo