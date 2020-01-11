package com.fateindestiny.android.sample.githubstars.data.local

import com.fateindestiny.tagdbfactory.*

/**
 * 즐겨찾기 DB의 정보 Class.
 * [TagDBFactory]의 [TagDBInfo]를 상속 받아 구현.
 *
 * @author Ki-man, Kim
 * @since 2020-01-11
 */
class DBInfo(dbFileName:String, dbVersion:Int) : TagDBInfo(dbFileName, dbVersion) {

    /**
     * 즐겨찾기 테이블.
     */
    @TagTable
    class TBL_FAVORIT_USER {
       companion object {
           /**
            * 테이블 이름.
            */
           @TagTableName
           const val NAME = "tbl_favorit_user"

           /**
            * DB Index Column.
            */
           @TagColumn(type = TagDBFactory.COLUMN_TYPE.INTEGER, isPrimaryKey = true, isAutoIncrement = true)
           const val IDX = "idx"

           /**
            * 사용자 이름 Column.
            */
           @TagColumn(type = TagDBFactory.COLUMN_TYPE.TEXT)
           const val LOGIN:String = "login"

           /**
            * 사용자 아바타 이미지 URL Column.
            */
           @TagColumn(type = TagDBFactory.COLUMN_TYPE.TEXT)
           const val AVATAR_URL = "avatar_url"
       }

    } // end of class TBL_FAVORIT_USER
} // end of class DBInfo