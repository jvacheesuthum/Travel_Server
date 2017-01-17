package com.travel_stories

import com.travel_stories.database.{DBCon, DatabaseConnection, TravelServerDatabase, LocalDataBase, MySqlDatabase}
import org.java_websocket.WebSocketImpl

/**
  * Created by jam414 on 20/10/16.
  */
object TravelServer {

  def main(args : Array[String]) {
    val serverPort = 1080
    val con:DBCon = new DatabaseConnection()
    val db:TravelServerDatabase = if(args.length > 0) new LocalDataBase else new MySqlDatabase(con)

    val communicator = new ServerCommunication(serverPort, new MessageHandler(db));
    println("Starting server on port " + serverPort);
    //WebSocketImpl.DEBUG = true
    communicator.start();


  }
}
