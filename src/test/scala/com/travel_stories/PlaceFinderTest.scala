package com.travel_stories

import com.travel_stories.database.TravelServerDatabase
import org.junit.Test

/**
  * Created by jam414 on 01/11/16.
  */
class PlaceFinderTest extends UnitSpec{
  private val verbosity: Boolean = true
  private[travel_stories] val db: TravelServerDatabase = null
  private[travel_stories] val p: PlaceFinder = new PlaceFinder(db, "AIzaSyDydfFUeuoJYb4inez08Apg4XDVTVPQDqM", verbosity)

  @Test def VeryExactPlace() {
    // the provided coordinates are very precise, the only result returned would be British museum.
    val result: String = p.webGetPlace(-0.1269566, 51.5194133)
    System.out.println("Expected British Museum, Got: " + result)
    org.junit.Assert.assertEquals("The provided coordinates should be The British Museum", result, "The British Museum")
  }

  @Test def RemotePlace() {
    // the provided coordinates are in a rural area, need to search up to radius of 1km.
    val result: String = p.webGetPlace(-2.004155, 53.271150)
    System.out.println("Expected any point of interest, Got: " + result)
    org.junit.Assert.assertNotNull("Should find something at 1km radius", result)
  }

  @Test def RandomPlaceInScotland() {
    // the provided coordinates are in a region with no point of interest, region name should be reported.
    val result: String = p.webGetPlace(-3.357819, 57.155797)
    System.out.println("Expected Inchrory, Got: " + result)
    org.junit.Assert.assertEquals("The provided coordinates should in Inchrory", result, "Inchrory")
  }

  @Test def NothingInTheMiddleOfNorthSea() {
    // the provided coordinates are in the middle of north sea.
    val result: String = p.webGetPlace(3.505735, 56.550987)
    System.out.println("Expected nothing, Got: " + result)
    org.junit.Assert.assertNull(result)
  }

}
