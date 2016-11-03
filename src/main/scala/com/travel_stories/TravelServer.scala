package com.travel_stories

import com.travel_stories.database.{LocalDataBase, MySqlDatabase, TravelServerDatabase}
import org.java_websocket.WebSocketImpl

/**
  * Created by jam414 on 20/10/16.
  */
object TravelServer {

  def main(args : Array[String]) {
    val serverPort = 1080
    println(args(0))
    val db:TravelServerDatabase = new MySqlDatabase// = if (args.length < 0) new MySqlDatabase else new LocalDataBase

    val communicator = new ServerCommunication(serverPort, new MessageHandler(db));
    println("Starting server on port " + serverPort);
    //WebSocketImpl.DEBUG = true
    communicator.start();


  }
}
