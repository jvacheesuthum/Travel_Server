package com.travel_stories

import java.net.URI

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake

/**
  * Created by jam414 on 21/10/16.
  */
class Client_scala(serverURI: String) extends WebSocketClient(new URI(serverURI)){
  var lastMessage:String = null;

  override def onError(e: Exception): Unit = {}

  override def onMessage(s: String): Unit = lastMessage = s

  override def onClose(i: Int, s: String, b: Boolean): Unit = {}

  override def onOpen(serverHandshake: ServerHandshake): Unit = {}

  def getLastMessage = lastMessage
}
