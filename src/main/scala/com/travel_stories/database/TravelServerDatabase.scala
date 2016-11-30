package com.travel_stories.database

/**
  * Created by jam414 on 24/10/16.
  */
import java.util.GregorianCalendar;

trait TravelServerDatabase {

  @throws(classOf[LocationNotFoundException])
  def getName(longitude:Double, latitude:Double):Place

  @throws(classOf[LocationNotFoundException])
  def getLocation(name:String):Place
  
  def storeName(name:String, longitude:Double, latitude:Double):Unit
  
  @throws(classOf[LocationNotFoundException])
  def nearbyPlace(longitude: Double, latitude: Double, user:Int):Array[Place]
  
  def storeTimeLineEntry(location:BigInt, start:GregorianCalendar, end:GregorianCalendar, user:Int, trip:BigInt):Unit

  def storeTrip(user:Int, name:String, start:GregorianCalendar, end:GregorianCalendar):BigInt

}
