package com.travel_stories

import java.io.ByteArrayInputStream
import java.nio.ByteBuffer
import javax.imageio.ImageIO

import com.travel_stories.database.TravelServerDatabase
import com.google.gson.Gson

/**
  * Created by jam414 on 20/10/16.
  */
class MessageHandler(db:TravelServerDatabase) { //(socketConn:SocketNetworkConnection) {

  val placeFinder = new PlaceFinder(db,"AIzaSyDydfFUeuoJYb4inez08Apg4XDVTVPQDqM")
  val suggestion = new Suggestion(db)
  val gson:Gson = new Gson()
  val SIZE_OF_INT = 4


  def onMessage(message:String):String = {

    //var values:String;
    val request = message.split(":")
    val values:String =
      request(0) match {
      case "timeline_address" => "timeline_address:" + nameRequest(request(1)).mkString("@")
      case "nearby_place" => "nearby_place:" + nearbyPlace(request.tail.mkString(":"))
      case "timeline_share" => "timeline_share:" + submitTimeline(request.tail.mkString(":"))
      case "get_location" => "get_location:" + locationRequest(request(1))
      case "Login" => "Jimmys Finished" //Do Databse stuff
      case _ => "A new failure message"

      }

    values

  }
  def onMessage(message:ByteBuffer):Unit = {


    val sizeOfName = message.getInt(0)
    val sizeOfInfoByte = sizeOfName + SIZE_OF_INT
    val name = new Array[Byte](sizeOfInfoByte)
    val getName = message.get(name)

    val image = new Array[Byte](message.remaining())
    message.get(image)
    image.drop(sizeOfInfoByte)


    val in = new ByteArrayInputStream(image)


    val st = new String(name.drop(SIZE_OF_INT), "UTF-8")

    val t = new TravelPhotos("images/" + st + ".png")

    val bi = ImageIO.read(in)
    t.upload(bi)
  }

  def nameRequest(loc: String): Array[String] = {
    print("message handler: NameReq")
    val locationList = loc.split("@")
    var l = List[String]()

    for(location <- locationList;
        s = location.split(",");
        long = s(0).toDouble;
        lat = s(1).toDouble;
        place = placeFinder.getPlace(long,lat).getName()
        )yield place //l = long::lat::l
  }

  
  /*
   * to be used when app is ready
  def nameRequest(loc: String): Array[String] = {
    print("message handler: NameReq")
    val locationList = loc.split("@")
    var l = List[String]()

    for(location <- locationList;
        s = location.split(",");
        long = s(0).toDouble;
        lat = s(1).toDouble;
        place = placeFinder.getPlace(long,lat);
        result = place.getKey().toString() + ',' + place.getName()
        )yield result //l = long::lat::l
  }

  */
  
  def locationRequest(name: String):String = {
    val place = placeFinder.locationFromName(name)
    return place.getKey().toString() + ',' + place.getLongitude() + ',' + place.getLatitude()
  }
  
  def nearbyPlace(input:String): String = {
    print("message handler: NearbyPlace")
    val s = input.split(",")
    val places = suggestion.placeSuggestions(s(0).toDouble, s(1).toDouble, s(2).toInt)
    
    return gson.toJson(places)
  }
  
  def submitTimeline(input:String): String= {
    print("message handler: submit timeline")
    val s = input.split("@")
    val tripkey:BigInt = suggestion.addTimeLine(s(0).toInt, s(1))
    return tripkey.toString()
  }




}
