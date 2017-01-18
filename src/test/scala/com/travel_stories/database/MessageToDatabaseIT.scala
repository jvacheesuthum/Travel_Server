package com.travel_stories.database

import com.travel_stories.{IntegrationSpec, MessageHandler}
import org.jmock.{Expectations, Mockery}

/**
  * Created by Gengar on 14/11/2016.
  */
class MessageToDatabaseIT extends IntegrationSpec{

  
/*
  val context:Mockery =new Mockery()
  val dbconnection:DBCon = context.mock(classOf[DBCon])
  val db = new MySqlDatabase(dbconnection)
  def CommFixture =
    new {
      val handler = new MessageHandler(db)
    }

  "an IntegrationTest" should "Do this" taggedAs DbTest in {
    context.checking(
        new Expectations{
          exactly(1).of(dbconnection).retreiveQuery("SELECT * FROM `geonames` WHERE `pkey` = 5160658127401;")
          exactly(1).of(dbconnection).retreiveQuery("SELECT * FROM `geonames` WHERE `pkey` = 5242291094301;")
        })
        CommFixture.handler.onMessage("timeline_address:-2.0041550,53.2711500@-3.3578190,57.1557970")
    
  }
  
  it should "Do this" taggedAs DbTest in {
    context.checking(
        new Expectations{
          atLeast(1).of(dbconnection).retreiveQuery("SELECT `popularity` FROM `geonames` WHERE `pkey` = 5160658127401;")
        })
        db.storeName("test", -2.0041550,53.2711500)
    
  }
  
  it should "Do this" taggedAs DbTest in {
    context.checking(
        new Expectations{
          exactly(1).of(dbconnection).retreiveQuery(
              "SELECT * FROM geonames WHERE name <> 'index ' AND ABS(longitude-0.00) < 5 AND ABS(latitude-0.00) < 5 AND pkey NOT IN (SELECT location FROM TimeLineEntries WHERE user =1) ORDER BY popularity DESC;")
        })
        CommFixture.handler.onMessage("nearby_place:0.00,0.00,1")
    
  }
  
  //TODO: get a real message for timeline submit
  
  /*
  def submitTimeLineTest = {
    context.checking(
        new Expectations{
          exactly(1).of(dbconnection).executeQuery("UPDATE `Trips` SET tripname=\'default name\', start=??, end=?? WHERE pkey=??;")
          atLeast(1).of(dbconnection).executeQuery("INSERT INTO `TimeLineEntries` (location, start, end, user, trip) VALUES (??, ??, ??, ??, ??);")
          atLeast(1).of(dbconnection).retreiveQuery("SELECT LAST_INSERT_ID() FROM TimeLineEntries;")
          atLeast(1).of(dbconnection).executeQuery("INSERT INTO `Photo` (tlentry, path) VALUES (??, '??');")
        })
        CommFixture.handler.onMessage("timeline_share:???????")
    
  }

  */

  it should "Do this" taggedAs DbTest in {
    context.checking(
        new Expectations{
          exactly(1).of(dbconnection).executeQuery("INSERT INTO `Trips` (user, tripname, start, end) VALUES(1, 'undefined', 0, 0);")
          exactly(1).of(dbconnection).retreiveQuery("SELECT LAST_INSERT_ID() FROM Trips;")
          exactly(1).of(dbconnection).executeQuery("INSERT INTO `Traces` (time, longitude, latitude, trip) VALUES (1, 1.0, 2.0, 1")
          exactly(1).of(dbconnection).executeQuery("INSERT INTO `Traces` (time, longitude, latitude, trip) VALUES (2, 3.0, 4.0, 1")
          exactly(1).of(dbconnection).executeQuery("INSERT INTO `Traces` (time, longitude, latitude, trip) VALUES (3, 5.0, 6.0, 1")
        })
        CommFixture.handler.onMessage("Final_map_trace:1@/1.0,2.0/3.0,4.0/5.0,6.0")
    
  }
  
*/
}
