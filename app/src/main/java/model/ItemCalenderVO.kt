package model


//달력 라이브러리 : https://github.com/INAH-PAK/Material-Calendar-View

// 내 닷홈 DB :  plogging.dothome.co.kr/myadmin
//  no	user_email	date	title	message	 location_latitude	location_longitude	file

data class ItemCalenderVO constructor(
                                        var date:String,
                                       var title:String,
                                       var message:String = "no Massege" ,
                                       var user_email:String = "non Email",
                                       var location_latitude:Double = 0.0,
                                       var location_longitude:Double = 0.0,
                                       var file:String = ""){

}
