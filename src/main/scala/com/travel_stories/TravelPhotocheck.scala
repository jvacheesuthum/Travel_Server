package com.travel_stories

import java.awt.image.BufferedImage
import java.io.{File, IOException}
import javax.imageio.ImageIO

/**
  * Created by Gengar on 23/11/2016.
  */
object TravelPhotocheck {

  def main(args: Array[String]): Unit = {

    val travelPhoto = new TravelPhotos("images/test.png")

    var img:BufferedImage = null

    try {
      img = ImageIO.read(new File("resources/test/images/lenna.png"))
      travelPhoto.upload(img)
    } catch
      {
        case e:IOException => e.printStackTrace
      }
  }

}
