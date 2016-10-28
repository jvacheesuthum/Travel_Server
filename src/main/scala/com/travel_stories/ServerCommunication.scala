package com.travel_stories

import java.net.InetSocketAddress

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer

/**
  * Created by jam414 on 20/10/16.
  */
class ServerCommunication(listenPort:Int ) extends WebSocketServer(new InetSocketAddress(listenPort)) {
  //might delete later
  var socketConn : SocketNetworkConnection = null;
  var messenger : MessageHandler = null

  override def onOpen(conn: WebSocket, handshake: ClientHandshake): Unit = {
    // might delete later
    socketConn = new SocketNetworkConnection (conn);
    messenger = new MessageHandler(socketConn);
  }

  override def onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean): Unit = {
    if(socketConn != null){ socketConn.send("It does Work :)")}
  }

  override def onMessage(conn: WebSocket, message: String): Unit = messenger.onMessage(message);

  override def onError(conn: WebSocket, ex: Exception): Unit = ???
}
