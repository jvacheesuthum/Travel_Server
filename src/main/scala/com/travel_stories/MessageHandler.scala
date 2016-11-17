package com.travel_stories

import com.travel_stories.database.TravelServerDatabase
import com.google.gson.Gson

/**
  * Created by jam414 on 20/10/16.
  */
class MessageHandler(db:TravelServerDatabase) { //(socketConn:SocketNetworkConnection) {

  val placeFinder = new PlaceFinder(db,"AIzaSyDydfFUeuoJYb4inez08Apg4XDVTVPQDqM")
  val suggestion = new Suggestion(db)
  val gson:Gson = new Gson()


  def onMessage(message:String):String = {

    //var values:String;
    val request = message.split(":")
    println(request(0))
    println(request(1))
    val values:String =
      request(0) match {
      case "timeline_address" => "timeline_address:" + nameRequest(request(1)).mkString("@")
      case "nearby_place" => "nearby_place:" + nearbyPlace(request.tail.mkString(":"))
      case "timeline_share" => "timeline_share: " + submitTimeline(request.tail.mkString(":"))
      case _ => "A new failure message"

      }

    values

  }

  def nameRequest(loc: String): Array[String] = {
    val locationList = loc.split("@")
    var l = List[String]()

    for(location <- locationList;
        s = location.split(",");
        long = s(0).toDouble;
        lat = s(1).toDouble;
        name = placeFinder.getPlace(long,lat)
        )yield name //l = long::lat::l
  }
  
  def nearbyPlace(input:String): String = {
    val s = input.split(",")
    val places = suggestion.placeSuggestions(s(1).toDouble, s(0).toDouble, s(2).toInt)
    
    return gson.toJson(places)
  }
  
  def submitTimeline(input:String): String= {
    val s = input.split("@")
    suggestion.addTimeLine(s(0).toInt, s(1))
    "OK"
  }




}
