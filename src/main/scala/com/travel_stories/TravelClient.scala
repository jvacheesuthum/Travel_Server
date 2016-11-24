package com.travel_stories

import java.awt.image.BufferedImage
import java.io.{ByteArrayInputStream, ByteArrayOutputStream, File, IOException}
import java.nio.ByteBuffer
import javax.imageio.ImageIO

import com.sun.xml.internal.ws.encoding.MtomCodec.ByteArrayBuffer
import com.travel_stories.database.MySqlDatabase

/**
  * Created by jam414 on 21/10/16.
  */
object TravelClient {

  def main(args: Array[String]): Unit = {
    val serverPort = 1080
    val url = "ws://localhost:1080" //"ws://cloud-vm-46-251.doc.ic.ac.uk:1080"//ws://localhost:1080"
    val test = "timeline_address:-0.1269566, 51.5194133@-2.004155, 53.271150@-3.357819, 57.155797@"
    val client = new Client(url)// + serverPort)

    /*val communicator = new ServerCommunication(serverPort, new MessageHandler(new MySqlDatabase));
    println("Starting server on port " + serverPort);
    communicator.start();*/

    client.connectBlocking()

    println("Sent:" + test)

    val travelPhoto = new TravelPhotos("images/test.png")

    var img:BufferedImage = null

    try {
      img = ImageIO.read(new File("resources/test/images/Lenna.png"))
      //travelPhoto.upload(img)
      val ba = new ByteArrayOutputStream()
      ImageIO.write(img, "png", ba)
      ba.flush()
      val imgbytest = ba.toByteArray

      client.send(test)
      client.send(imgbytest)
      println("WTF")

    } catch
      {
        case e:IOException => e.printStackTrace
      }



  }

}
