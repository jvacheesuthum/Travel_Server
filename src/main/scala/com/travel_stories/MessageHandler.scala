package com.travel_stories

import com.travel_stories.database.TravelServerDatabase
import com.google.gson.Gson

/**
  * Created by jam414 on 20/10/16.
  */
class MessageHandler(db:TravelServerDatabase) { //(socketConn:SocketNetworkConnection) {

  val placeFinder = new PlaceFinder(db,"AIzaSyDydfFUeuoJYb4inez08Apg4XDVTVPQDqM")
  val suggestion = new Suggestion(db)


  def onMessage(message:String):String = {

    //var values:String;
    val request = message.split(":")
    println(request(0))
    println(request(1))
    val values:String =
      request(0) match {
      case "timeline_address" => "timeline_address:" + nameRequest(request(1)).mkString("@")
      case "nearby_place" => "nearby_place:" + nearbyPlace(request(1))
      case _ => "You Didn't Send Anything Worthwhile"

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
    val gson:Gson = new Gson();
    return gson.toJson(places)
  }




}
