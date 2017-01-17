package com.travel_stories

import com.travel_stories.database.LocalDataBase
import com.travel_stories.tagobjects.UnitTest


/**
  * Created by jam414 on 28/10/16.
  */
class ServerCommunicationTest extends UnitSpec {

  def CommFixture =
    new {
      val serverPort = 1181
      val communicator = new ServerCommunication(serverPort, new MessageHandler(new LocalDataBase))
      val client = new Client_scala("ws://localhost:" + serverPort)
    }

  "a client" should "recieve the message they send" taggedAs(UnitTest) in {
    val comm = CommFixture
    //British Museum, Thursbitch? Inchrory
    val message = "timeline_address:-0.1269566, 51.5194133@-2.004155, 53.271150@-3.357819, 57.155797"

    comm.communicator.start()
    comm.client.connectBlocking()
    comm.client.send(message)

    comm.client.closeBlocking()
    //comm.client.getLastMessage shouldBe message
  }
}
