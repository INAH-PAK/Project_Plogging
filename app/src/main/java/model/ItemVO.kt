package model


//달력 라이브러리 : https://github.com/INAH-PAK/Material-Calendar-View

// 내 닷홈 DB :  plogging.dothome.co.kr/myadmin
//  no	user_email	date	title	message	 location_latitude	location_longitude	file

data class ItemVO constructor(var no :Int,
                              var user_email:String,
                              var title:String = ""){

}

data class Borad(var email_user:String,
                 var email_join_user:String,
                 var date:String,
                 var title:String,
                 var message:String,
                 var location_latitude:String,
                 var location_longitude:String,
                 var run_time:String,
                 var is_share:String,
                 var fav_num:String ){

}
5

data class
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