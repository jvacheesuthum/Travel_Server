package com.travel_stories

import java.net.InetSocketAddress

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer

/**
  * Created by jam414 on 20/10/16.
  */
class ServerCommunication(listenPort:Int, handler: MessageHandler ) extends WebSocketServer(new InetSocketAddress(listenPort)) {
  //var socketConn : SocketNetworkConnection = null;

  override def onOpen(conn: WebSocket, handshake: ClientHandshake): Unit = {
    // might delete later
    //socketConn = new SocketNetworkConnection (conn);
    //messenger = new MessageHandler(socketConn);
  }

  override def onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean): Unit = {}

  override def onMessage(conn: WebSocket, message: String) {

    handler.onMessage (new SocketNetworkConnection (conn), message)

  }

  override def onError(conn: WebSocket, ex: Exception): Unit = ???
}
