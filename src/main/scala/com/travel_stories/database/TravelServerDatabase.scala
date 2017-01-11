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
  
  def storeName(name:String, longitude:Double, latitude:Double):Place
  
  @throws(classOf[LocationNotFoundException])
  def nearbyPlace(longitude: Double, latitude: Double, user:BigInt):Array[Place]
  
  def storeTimeLineEntry(location:BigInt, start:GregorianCalendar, end:GregorianCalendar, user:BigInt, trip:BigInt):BigInt

  def storeTrip(user:BigInt, name:String, start:GregorianCalendar, end:GregorianCalendar):BigInt

  def storeTrace(tripkey:BigInt, time:Int, long:Double, lat:Double):Unit
  def linkPhoto(tlentry:BigInt, path:String):Unit
}
