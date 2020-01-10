package com.fateindestiny.android.sample.githubstars.util

class Util private constructor() {

    companion object {
        val initChars = listOf(
            'ㄱ',
            'ㄲ',
            'ㄴ',
            'ㄷ',
            'ㄸ',
            'ㄹ',
            'ㅁ',
            'ㅂ',
            'ㅃ',
            'ㅅ',
            'ㅆ',
            'ㅇ',
            'ㅈ',
            'ㅉ',
            'ㅊ',
            'ㅋ',
            'ㅌ',
            'ㅍ',
            'ㅎ'
        )

        fun getInitialChar(str: String): String = if (str.isNotEmpty()) {
            val char = str[0]
            if (char >= (0xAC00).toChar()) {
                // 첫 문자가 한글 코드 영역일 경우.
                val uni = char.toInt() - 0xAC00
                val choSungIdx = ((uni - (uni.rem(28))) / 28) / 21
                initChars[choSungIdx].toString()
            } else {
                // 그외의 문자는 그대로 첫 문자를 반환.
                char.toString()
            }
        } else {
            // 문자열이 비어 있다면 그 문자열 그대로 반환.
            str
        }

    }
}