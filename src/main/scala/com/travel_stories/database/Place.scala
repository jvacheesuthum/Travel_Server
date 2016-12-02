package com.travel_stories.database
import java.math.BigInteger


class Place(key:BigInteger, name:String, latitude:Double, longitude:Double){
  
  var popularity:Int = 0
  
  def setPopularity(p:Int) = popularity = p
  
  def getName():String = name
  def getKey():BigInteger = key
  def getLatitude():Double = latitude
  def getLongitude():Double = longitude
  def getPopularity():Int = popularity  

}
