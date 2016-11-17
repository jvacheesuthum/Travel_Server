package com.travel_stories.database


class Place(key:BigInt, name:String, latitude:Double, longitude:Double){
  
  var popularity:Int = 0
  
  def setPopularity(p:Int) = popularity = p
  
  def getName():String = name
  def getKey():BigInt = key
  def getLatitude():Double = latitude
  def getLongitude():Double = longitude
  def getPopularity():Int = popularity
  

}
