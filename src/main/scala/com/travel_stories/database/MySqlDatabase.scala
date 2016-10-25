package com.travel_stories.database

/**
  * Created by jam414 on 24/10/16.
  */
class MySqlDatabase extends TravelServerDatabase {

  val dbConnection = new DatabaseConnection()
  dbConnection.connect

  override def getName(longitude: Double, latitude: Double): String = {
    val sb = new StringBuilder
    sb.append("SELECT `name` FROM `geonames` WHERE `longitude` = ")
    sb.append(longitude)
    sb.append(" AND `latitude` = ")
    sb.append(latitude)
    sb.append(";")

    val query = sb.toString

    val result = dbConnection.retreiveQuery(query)

    if (result.isEmpty) throw LocationNotFoundException("Location not in db");
    else{
      //gross code
      result.head("name").asInstanceOf[String];
    }


  }
}
