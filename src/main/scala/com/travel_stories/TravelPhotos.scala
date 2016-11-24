package com.travel_stories

import java.awt.image.BufferedImage
import java.io.{File, IOException}
import javax.imageio.ImageIO

/**
  * Created by Gengar on 23/11/2016.
  */
class TravelPhotos(path:String) {

  def upload(image:BufferedImage):Unit = {
    try {
      // retrieve image
      val outputfile = new File(path)
      ImageIO.write(image, "png", outputfile)
    }catch
      {
      case e:IOException => e.printStackTrace()
      }
    }

}
