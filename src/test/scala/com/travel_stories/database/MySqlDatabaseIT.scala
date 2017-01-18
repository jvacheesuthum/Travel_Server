package com.travel_stories.database

import com.travel_stories.IntegrationSpec

/**
  * Created by jam414 on 28/10/16.
  */
class MySqlDatabaseIT extends IntegrationSpec{
  /*def dbFixture =
    new {
      val mySQLDb = new MySqlDatabase(new DatabaseConnection());
    }*/

  val db = new MySqlDatabase(new DatabaseConnection())

  "A MySql Database Query for the name with logitude and latitude of an already found place" should
    "be in the database" taggedAs DbTest in {
    //The Computing Department has already been added
   //val db = dbFixture.mySQLDb
    val name:String = db.getName(-0.1794343, 51.4988160).getName()
    name shouldBe "Computing Department"
  }
  
  "A MySql Database Query places nearby Computing Department" should
    "have at least one result" taggedAs DbTest in {
    //The Computing Department and nearby places exist in db
    //val db = dbFixture.mySQLDb
    val items:Int = db.nearbyPlace(-0.1794343, 51.4988160, 1).length
    items >0 shouldBe true
  }
  /*
  "A MySql Database Query for trip 1" should
    "have default timeline entry that starts at 1" taggedAs DbTest in {
    //The Computing Department and nearby places exist in db
    //val db = dbFixture.mySQLDb
    val start:String = db.getTrip(1)(0).start.toString()
    start shouldBe "1"
  }
*/

  //println(testString);

}
