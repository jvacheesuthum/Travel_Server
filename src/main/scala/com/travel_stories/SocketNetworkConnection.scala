package com.travel_stories

import org.java_websocket.WebSocket
import org.java_websocket.exceptions.WebsocketNotConnectedException;

/**
  * Created by jam414 on 20/10/16.
  */
class SocketNetworkConnection(webSocket: WebSocket) {

  def send(message:String): Unit = {
    try {
      webSocket.send(message)
    }catch
      {
        case notConnected: WebsocketNotConnectedException => print("Connection Closed ...");
      }

  }

}
