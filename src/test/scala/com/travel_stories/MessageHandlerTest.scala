package com.travel_stories

import com.travel_stories.database.LocalDataBase
import com.travel_stories.tagobjects.UnitTest

/**
  * Created by Gengar on 14/11/2016.
  */
class MessageHandlerTest extends UnitSpec{

  def CommFixture =
    new {
      val handler = new MessageHandler(new LocalDataBase)
    }

  "a MessageHandler" should "Return the CSlocations from the gps coords send in \"timeline_address\" request" taggedAs UnitTest in {
    val comm = CommFixture
    //British Museum, Thursbitch? Inchrory
    val message = "timeline_address:-0.1269566, 51.5194133@-2.004155, 53.27115@-3.357819, 57.155797"

    val ret = comm.handler.onMessage(message)

    ret shouldBe "timeline_address:0,The British Museum@0,Thursbitch@0,Inchrory"
  }



}
