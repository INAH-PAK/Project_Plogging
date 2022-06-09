package model

import java.io.Serializable

//달력 라이브러리 : https://github.com/INAH-PAK/Material-Calendar-View

// 내 닷홈 DB :  plogging.dothome.co.kr/myadmin
//  no	user_email	date	title	message	 location_latitude	location_longitude	file

data class Item constructor(var user_email:String, var title:String = "")

data class ScheduleVO(
    val user_email: String ,
    val date:String= "",
    val title:String,
    val message:String= "",
    val location_latitude:String= "",
    val location_longitude:String= "",
    val file:String= "",
    val is_share: String= "false")

data class Borad(val email_user:String= "",
                 val email_join_user:String= "",
                 val date:String= "",
                 val title:String,
                 val message:String = "",
                 val location_latitude:String= "",
                 val location_longitude:String= "",
                 val run_time:String= "",
                 val is_share:String= "",
                 val fav_num:String  ="false") : Serializable

data class FavHistory(var user_email:String,
                      var board_num:String )

data class Marker(var user_email:String,
                   var location_latitude:String,
                   var location_longitude:String,
                   var type:Int)

data class Garden(var user_email:String,
                  var ear_challenge:String,
                  var ear_complete:String )















//
//
//    (
//                                        var date:String,
//                                       var title:String,
//                                       var message:String = "no Massege" ,
//                                       var user_email:String = "non Email",
//                                       var location_latitude:Double = 0.0,
//                                       var location_longitude:Double = 0.0,
//                                       var file:String = ""){
//
//}
