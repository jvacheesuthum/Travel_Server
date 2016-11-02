package com.travel_stories

/**
  * Created by jam414 on 21/10/16.
  */
object TravelClient {

  def main(args: Array[String]): Unit = {
    val serverPort = 1080
    val test = "timeline_address:256.0,325.7@542.25,452.5654@541.6,641.36@"
    val client = new Client("http://cloud-vm-46-251.doc.ic.ac.uk:" + serverPort)

    client.connectBlocking();
    client.send(test);
    while (true){

    }
    client.closeBlocking();
    println("OKAY?");


  }

}
