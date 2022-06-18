package model

// 앱 로그인시 저장될 사용자의
data class UserAccount(val id:String,
                       val email:String ,
                       val loginType:Int)

// id 값은, 이 사람이 정확히 누구인지를 식별할 수 있는 각 로그인 API에서 제공하는 고유의 식별자.
// Email ->  UID
// 카카오 -> id
// 구글   -> id

// 공용으로 사용 될 email.
