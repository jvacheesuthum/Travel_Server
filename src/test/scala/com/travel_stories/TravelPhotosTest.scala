/*package com.travel_stories

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import java.
/**
  * Created by Gengar on 23/11/2016.
  */
//class TravelPhotosTest extends UnitSpec{

  def CommFixture =
    new {
      val travelPhoto = new TravelPhotos("test.png")
    }

  var img:BufferedImage = null

  try {
    img = ImageIO.read(new File("resources/test/images/lenna.png"))
    CommFixture.travelPhoto.upload(img)
  } catch
    {
      case e:IOException => e.printStackTrace
  }

}
*/