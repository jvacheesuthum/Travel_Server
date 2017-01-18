package com.travel_stories;

import com.travel_stories.database.{LocalDataBase, TravelServerDatabase}
import com.travel_stories.tagobjects.UnitTest;

class PlaceFinderScalaTest extends UnitSpec{
  /* Cant test java code with scala

    val verbosity:Boolean = true

    val db:TravelServerDatabase = new LocalDataBase
    val p:PlaceFinder  = new PlaceFinder(db, "AIzaSyDydfFUeuoJYb4inez08Apg4XDVTVPQDqM", verbosity);

    "a Place Finder" should "Return a Very Exact Place when given very exact coordinates" taggedAs UnitTest in {
        // the provided coordinates are very precise, the only result returned would be British museum.
        val result:String = p.webGetPlace(-0.1269566, 51.5194133);
        println("Expected British Museum, Got: " + result);
        result shouldBe  "The British Museum"
    }

    it should "Find something interesting at a 1km radius when given a Remote Place" taggedAs UnitTest in {
        // the provided coordinates are in a rural area, need to search up to radius of 1km.
        val result:String = p.webGetPlace(-2.004155, 53.271150);
        System.out.println("Expected any point of interest, Got: " + result);
        result should not be null
    }


    it should "Return the region name when the provided coordinates are in a region with no point of interest," +
            "ie a RandomPlaceInScotland" taggedAs UnitTest in {

        // the provided coordinates are in a region with no point of interest, region name should be reported.
        val result:String  = p.webGetPlace(-3.357819, 57.155797)
        System.out.println("Expected Inchrory, Got: " + result)
        result shouldBe "Inchrory"
    }


    it should "return nothing when given coordinates of Nothing In The Middle Of NorthSea" taggedAs UnitTest in {
        // the provided coordinates are in the middle of north sea.
        val result:String  = p.webGetPlace(3.505735, 56.550987)
        System.out.println("Expected nothing, Got: " + result)
        result shouldBe null
    }
*/
}