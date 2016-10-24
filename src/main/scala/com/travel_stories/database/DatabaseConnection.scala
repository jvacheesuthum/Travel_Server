package com.travel_stories.database

import java.sql._;

/**
  * Created by jam414 on 24/10/16.
  */
class DatabaseConnection {
  //JDBC drivername and Database URL
  private val DB_URL = "jdbc:mysql://localhost:3306/location"

  //Database credentials
  private val USER = "TravelServer"
  private val PASS = "TravelServer"

  var conn:Connection = null

  def connect():Unit = {

    try{
      //Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver")
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }
    catch
      {
        case e: Exception => e.printStackTrace();
      }
  }

  def retreiveQuery(sql:String):List[Map[String, Object]] = {
    val databaseValues:List[Map[String, Object]] = List()

    try {

      val stmt: Statement = conn.createStatement()
      val resSet: ResultSet = stmt.executeQuery(sql)


      val metaData: ResultSetMetaData = resSet.getMetaData
      val numColumns: Integer = metaData.getColumnCount

      while (resSet.next()) {
        var row: Map[String, Object] = Map()
        var i = 0
        for (i <- 1 to numColumns) {
          val name: String = metaData.getCatalogName(i)
          val value: Object = resSet.getObject(i)
          row + (name -> value)
        }
        row :: databaseValues
      }

      resSet.close()
      stmt.close()
    }
    catch {
      case e: Exception => connect(); e.printStackTrace();
    }

    return databaseValues;
    }

  def executeQuery(sql:String):Int ={
    var result:Int = 0;
    try {
      val stmt:Statement = conn.createStatement
      result = stmt.executeUpdate(sql)

      stmt.close();
    }
    catch {
      case e: Exception => connect(); e.printStackTrace();
    }
    return result

    }


  }