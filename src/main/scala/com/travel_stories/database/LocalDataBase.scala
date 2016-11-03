package com.travel_stories.database

/**
  * Created by Gengar on 03/11/2016.
  */
class LocalDataBase extends TravelServerDatabase{
  @throws(classOf[LocationNotFoundException])
  override def getName(longitude: Double, latitude: Double): String = longitude.toString + latitude.toString

  override def storeName(name: String, longitude: Double, latitude: Double): Unit = {}
}
