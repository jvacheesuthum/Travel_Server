package com.travel_stories

import java.net.URI

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake

/**
  * Created by jam414 on 21/10/16.
  */
class Client_scala(serverURI: String) extends WebSocketClient(new URI(serverURI)){

  override def onError(e: Exception): Unit = println("Client on error!")

  override def onMessage(message: String): Unit = println("message received: " + message)

  override def onClose(i: Int, s: String, b: Boolean): Unit = println(System.out.println("Client closed"))

  override def onOpen(serverHandshake: ServerHandshake): Unit = println("Client open")
}