package com.travel_stories

/**
  * Created by jam414 on 20/10/16.
  */
object TravelServer {

  def main(args : Array[String]) {
    val serverPort = 1080

    val communicator = new ServerCommunication(serverPort, new MessageHandler);
    println("Starting server on port " + serverPort);
    communicator.start();


  }
}
