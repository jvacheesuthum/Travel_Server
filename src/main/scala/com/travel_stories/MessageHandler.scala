package com.travel_stories

/**
  * Created by jam414 on 20/10/16.
  */
class MessageHandler(socketConn:SocketNetworkConnection) {

  def onMessage(message:String) ={
    socketConn.send(message)
  }



}
