package com.travel_stories.database

import java.sql._

trait DBCon {
  def retreiveQuery(sql:String):List[Map[String, Object]]
  def executeQuery(sql:String):Int
  def connect():Unit


}
