package com.wookie_soft.inah.model

class User {

    companion object{

        const val EMAIL = 11
        const val GOOGLE = 22
        const val KAKAOLOGIN = 33

        const val RECYCLE = 1
        const val RESTROOM = 2
        const val CAUTION = 3

        val glovalItemList : MutableList<ScheduleVO> = mutableListOf<ScheduleVO>()

        var loginType:Int = -1

        var titlBundel : String = ""
        var j: Borad = Borad(title = titlBundel)
        var isisis = false


    }
}