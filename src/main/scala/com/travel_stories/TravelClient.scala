package com.travel_stories

/**
  * Created by jam414 on 21/10/16.
  */
object TravelClient {

  def main(args: Array[String]): Unit = {
    val serverPort = 1080
    val client = new Client("http://cloud-vm-46-251.doc.ic.ac.uk:" + serverPort)

    client.connectBlocking();
    client.send("HI");
    client.closeBlocking();
    println("OKAY?");
  }

}
