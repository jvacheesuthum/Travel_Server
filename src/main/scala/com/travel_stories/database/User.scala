package com.travel_stories.database


class User(key:Int, first:String, last:String, longitude:Double, latitude:Double){
  
  var visited:String = ""
  
  def addVisited(placeName:String):String = {
    if (!visited.equals("")) {
      visited.+(", ")
    }
    visited.+(placeName)
    return visited
  }
  
  def clearVisited() = visited=""
  
  def getFirst():String = first
  def getLast():String = last
  def getKey():Int = key
  def getVisited():String = visited
  def getLocation():(Double,Double) = (longitude, latitude)

}
