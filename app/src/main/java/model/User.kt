package model

class User {

    companion object{

        const val GOOGLE = 1
        const val KAKAOLOGIN = 2
        const val EMAIL = 3

        const val RECYCLE = 1
        const val RESTROOM = 2
        const val CAUTION = 3

        val glovalItemList : MutableList<ScheduleVO> = mutableListOf<ScheduleVO>()

        var loginType:Int = -1

        var titlBundel : String = ""
        var j:Borad = Borad(title = titlBundel)
        var isisis = false


    }
}