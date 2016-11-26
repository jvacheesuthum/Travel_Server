package com.travel_stories.database

import slick.driver.MySQLDriver.api._



class SlickDB{
  
  private val DB_URL = "jdbc:mysql://localhost:3306/locations?autoReconnect=true&useSSL=false"

  //Database credentials
  private val USER = "TravelServer"
  private val PASS = "TravelServer"
  
  val db = Database.forURL(DB_URL, USER, PASS)
  
  
}
