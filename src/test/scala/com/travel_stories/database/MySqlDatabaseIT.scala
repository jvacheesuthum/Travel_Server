package com.travel_stories.database

import com.travel_stories.IntegrationSpec

/**
  * Created by jam414 on 28/10/16.
  */
class MySqlDatabaseIT extends IntegrationSpec{

  def dbFixture =
    new {
      val mySQLDb = new MySqlDatabase;
      //val testString = dbtest.getName(1024,1024);
    }

  "A MySql Database Query for the name with logitude and latitude of an already found place" should
    "be in the database" taggedAs DbTest in {
    //The British Museum has already been added by countless tests
    val db = dbFixture.mySQLDb
    db.getName(-0.1269566, 51.5194133) shouldBe "The British Museum"
  }


  //println(testString);

}
