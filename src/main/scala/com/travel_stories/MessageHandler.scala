package com.travel_stories

import com.travel_stories.database.TravelServerDatabase

/**
  * Created by jam414 on 20/10/16.
  */
class MessageHandler(db:TravelServerDatabase) { //(socketConn:SocketNetworkConnection) {


  def onMessage(socketConn:SocketNetworkConnection, message:String) = {

    var values = Array[String]()
    val request = message.split(":")
    values =
      request(0) match {
      case "timeline_address" => nameRequest(request(1));

      }

    socketConn.send(values.toString)



  }

  def nameRequest(loc: String): Array[String] = {
    val locationList = loc.split("@")
    var l = List[String]()

    for(location <- locationList;
        s = location.split(",");
        long = s(0).toDouble;
        lat = s(1).toDouble;
        name = db.getName(long,lat)
        )yield name //l = long::lat::l
  }




}
