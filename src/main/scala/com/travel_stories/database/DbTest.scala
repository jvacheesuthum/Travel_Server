package com.travel_stories.database

/**
  * Created by jam414 on 24/10/16.
  */
object DbTest {

  def main(args: Array[String]): Unit = {
    var dbtest = new MySqlDatabase;
    var testString = dbtest.getName(1024,1024);
    println(testString);
  }

}
