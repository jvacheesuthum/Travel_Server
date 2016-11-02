package com.travel_stories

import java.net.URI

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake

/**
  * Created by jam414 on 21/10/16.
  */
class Client(serverURI: String) extends WebSocketClient(new URI(serverURI)){
  override def onError(e: Exception): Unit = {}

  override def onMessage(s: String): Unit = println(s)

  override def onClose(i: Int, s: String, b: Boolean): Unit = {}

  override def onOpen(serverHandshake: ServerHandshake): Unit = {}
}
