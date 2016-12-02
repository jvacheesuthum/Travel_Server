package com.travel_stories.database

import java.util.GregorianCalendar;
/**
  * Created by Gengar on 03/11/2016.
  */
class LocalDataBase extends TravelServerDatabase{
  @throws(classOf[LocationNotFoundException])
  override def getName(longitude: Double, latitude: Double): Place = {
    Thread.sleep(1000);
    longitude + ", "  + latitude
    null
  }
  override def getLocation(name:String):Place = null
  override def storeName(name: String, longitude: Double, latitude: Double):Place = null
  
  override def nearbyPlace(longitude: Double, latitude: Double, user:BigInt):Array[Place] = null


  override def storeTimeLineEntry(location:BigInt, start:GregorianCalendar, end:GregorianCalendar, user:BigInt, trip:BigInt):Unit =null
  override def storeTrip(user:BigInt, name:String, start:GregorianCalendar, end:GregorianCalendar):BigInt = null
}
