package com.travel_stories.integration_tests

import com.travel_stories.database.MySqlDatabase

/**
  * Created by jam414 on 28/10/16.
  */
class MySqlDatabaseIT extends IntegrationSpec{

  def dbFixture =
    new {
      val mySQLDb = new MySqlDatabase;
      //val testString = dbtest.getName(1024,1024);
    }

  "A MySql Database Query for the name with logitude and latitude 1024" should "return name test" taggedAs(DbTest) in {
    val db = dbFixture.mySQLDb;
    db.getName(1024,1024) shouldBe "Test"
  }


  //println(testString);

}
