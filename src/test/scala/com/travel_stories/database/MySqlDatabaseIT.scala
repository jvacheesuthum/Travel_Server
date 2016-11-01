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

  "A MySql Database Query for the name with logitude and latitude 1024" should "return name test" taggedAs(DbTest) ignore {
    val db = dbFixture.mySQLDb;
    db.getName(1024,1024) shouldBe "Test"
  }


  //println(testString);

}
