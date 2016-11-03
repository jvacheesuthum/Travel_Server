package com.travel_stories

import com.travel_stories.database.MySqlDatabase

/**
  * Created by jam414 on 21/10/16.
  */
object TravelClient {

  def main(args: Array[String]): Unit = {
    val serverPort = 1085
    val url = "ws://cloud-vm-46-251.doc.ic.ac.uk:1080"//ws://localhost:1080"
    val test = "timeline_address:256.0,325.7@542.25,452.5654@541.6,641.36@"
    val client = new Client(url)// + serverPort)

    /*val communicator = new ServerCommunication(serverPort, new MessageHandler(new MySqlDatabase));
    println("Starting server on port " + serverPort);
    communicator.start();*/

    client.connectBlocking()

    println("Sent:" + test)

    client.send(test)


  }

}
