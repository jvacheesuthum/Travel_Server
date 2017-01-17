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
    //The Huxley Building has already been added
    val db = dbFixture.mySQLDb
    val name:String = db.getName(-0.1796177, 51.4987336).getName()
    name shouldBe "Huxley Building"
  }


  //println(testString);

}
