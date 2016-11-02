package com.travel_stories

import com.travel_stories.database.MySqlDatabase

/**
  * Created by jam414 on 28/10/16.
  */
class ServerCommunicationTest extends UnitSpec{

  def CommFixture =
    new {
      val serverPort = 1080
      val communicator = new ServerCommunication(serverPort, new MessageHandler(new MySqlDatabase))
      val client = new Client("http://localhost:" + serverPort);
    }

  "a client" should "recieve the message they send" taggedAs(UnitTest) ignore {
    val comm = CommFixture
    val message = "Hi"
    comm.communicator.start()
    comm.client.connectBlocking()
    comm.client.send(message)

    comm.client.closeBlocking()
    //comm.client.getLastMessage shouldBe message
  }


/*
  def main(args: Array[String]): Unit = {


    client.connectBlocking();
    client.send("HI");
    client.closeBlocking();
    println("OKAY?");
    */
}
