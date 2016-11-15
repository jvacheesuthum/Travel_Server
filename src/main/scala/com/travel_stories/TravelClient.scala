package com.travel_stories

import com.travel_stories.database.MySqlDatabase

/**
  * Created by jam414 on 21/10/16.
  */
object TravelClient {

  def main(args: Array[String]): Unit = {
    val serverPort = 1080
    val url = "ws://localhost:1080"//ws://localhost:1080"
    val test = "timeline_address:-0.1269566, 51.5194133@-2.004155, 53.271150@-3.357819, 57.155797@"
    val client = new Client(url)// + serverPort)

    /*val communicator = new ServerCommunication(serverPort, new MessageHandler(new MySqlDatabase));
    println("Starting server on port " + serverPort);
    communicator.start();*/

    client.connectBlocking()

    println("Sent:" + test)

    client.send(test)


  }

}
