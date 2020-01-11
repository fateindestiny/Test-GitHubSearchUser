package com.fateindestiny.android.sample.githubstars.data.local

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.fateindestiny.android.sample.githubstars.data.UserVO
import com.fateindestiny.android.sample.githubstars.util.Util
import com.fateindestiny.tagdbfactory.TagDBFactory
import java.sql.SQLException

/**
 * Local DB를 조작하는 Helper Class.
 * [TagDBFactory] 라이브러리를 사용하여 처리.
 *
 * 기능
 * 1. 즐겨찾기로 추가한 사용자 조회
 * 2. 사용자 삭제
 * 3. 사용자 여부 체크
 *
 * @author Ki-man, Kim
 * @since 2020-01-11
 */
object DBHelper : TagDBFactory() {

    // db 정보
    private val dbInfo = DBInfo("favorit.db", 1)

    // DB를 조작하기 위한 [SQLiteDatabase]
    private val db: SQLiteDatabase by lazy { open(dbInfo) }

    /**
     * 즐겨찾기 테이블에 저장된 사용자를 검색하기 위한 함수.
     *
     * @param userName 조회할 사용자 정보. 공백일 경우 모든 사용자 정보를 조회.
     * @return 조회된 사용자 정보 목록 [ArrayList<UserVO>]
     */
    fun selectFavoritUser(userName: String): ArrayList<UserVO> {
        val list: ArrayList<UserVO> = arrayListOf()
        try {
            val cursor = db.query(
                DBInfo.TBL_FAVORIT_USER.NAME, null,
                // userName이 없으면 모든 조건 없이, userName이 있으면 해당 이름 값으로 조회.
                if (userName.isEmpty()) null else "${DBInfo.TBL_FAVORIT_USER.LOGIN} like \"%$userName%\"",
                null, null, null,
                "${DBInfo.TBL_FAVORIT_USER.LOGIN} ASC"
            )
            if (cursor.moveToFirst()) {
                do {
                    list.add(
                        UserVO(
                            getStringByColumnName(cursor, DBInfo.TBL_FAVORIT_USER.LOGIN),
                            getStringByColumnName(cursor, DBInfo.TBL_FAVORIT_USER.AVATAR_URL)
                        ).apply {
                            // 즐겨찾기 데이터임을 설정.
                            isFavorit = true
                            // 초성 데이터를 설정.
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

    /**
     * 사용자 이름으로 즐겨찾기 여부(DB에 사용자가 있는지 조회)를 체크하는 함수.
     *
     * @param userName 조회할 사용자 이름
     * @return 즐겨찾기 여부 [Boolean]
     */
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

    /**
     * 즐겨찾기 테이블에 사용자 정보를 추가하는 함수.
     *
     * @param user 추가할 사용자 정보.
     * @return DB 데이터 추가 결과 값.
     */
    fun insertFavoritUser(user: UserVO): Long {
        var result = 0L
        try {
            result = db.insert(DBInfo.TBL_FAVORIT_USER.NAME, null, user.getContentValues())
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * 즐겨찾기 테이블의 사용자 정보를 삭제하는 함수.
     *
     * @param user 삭제할 사용자 정보.
     * @return DB 데이터 삭제 결과 값.
     */
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

    /**
     * DB 조회시 데이터를 조회하기 위해 Column 이름의 데이터를 조회하기 위한 함수.
     *
     * @param cursor 조회할 DB [Cursor]
     * @param name 조회한 정보의 Column name.
     * @return 조회된 데이터 [String]
     */
    private fun getStringByColumnName(cursor: Cursor, name: String): String =
        when (val idx = cursor.getColumnIndex(name)) {
            -1 -> ""
            else -> cursor.getString(idx)
        }

} // end of class DBHelper