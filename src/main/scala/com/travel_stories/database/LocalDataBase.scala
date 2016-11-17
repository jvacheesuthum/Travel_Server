package com.travel_stories.database

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

  override def storeName(name: String, longitude: Double, latitude: Double): Unit = {}
  
  override def nearbyPlace(longitude: Double, latitude: Double, user:Int):Array[Place] = null
}
