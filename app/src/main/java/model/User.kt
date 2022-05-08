package model

class User {

    companion object{

        const val GOOGLE = 1
        const val KAKAOLOGIN = 2
        const val EMAIL = 3

        var loginType:Int = -1

    }
}