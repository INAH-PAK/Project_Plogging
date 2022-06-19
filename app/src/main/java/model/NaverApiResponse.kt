package model

import java.util.*

data class NaverApiResponse(
  //  var lastBuildDate : Date,
    val total:Int,
    val start:Int,
    val display:Int,
    val items:MutableList<NaverApiItems>
)
data class NaverApiItems(
    val title:String,
    val originallink:String,
    val link:String,
    val description:String,
 //   val pubDate:Date,
)
