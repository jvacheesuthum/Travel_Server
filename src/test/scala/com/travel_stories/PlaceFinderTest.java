package com.travel_stories;

import org.junit.Test;

import com.travel_stories.database.TravelServerDatabase;

public class PlaceFinderTest {
	
	private boolean verbosity = true;
	TravelServerDatabase db;
	PlaceFinder p = new PlaceFinder(db, "AIzaSyDydfFUeuoJYb4inez08Apg4XDVTVPQDqM", verbosity);
	
	@Test
	public void VeryExactPlace() {
		// the provided coordinates are very precise, the only result returned would be British museum.
		String result = p.webGetPlace(-0.1269566, 51.5194133);
		System.out.println("Expected British Museum, Got: " + result);
		org.junit.Assert.assertEquals("The provided coordinates should be The British Museum", result, "The British Museum");
	}
	
	@Test
	public void RemotePlace() {
		// the provided coordinates are in a rural area, need to search up to radius of 1km.
		String result = p.webGetPlace(-2.004155, 53.271150);
		System.out.println("Expected any point of interest, Got: " + result);
		org.junit.Assert.assertNotNull("Should find something at 1km radius", result);
	}
	
	@Test
	public void RandomPlaceInScotland() {
		// the provided coordinates are in a region with no point of interest, region name should be reported.
		String result = p.webGetPlace(-3.357819, 57.155797);
		System.out.println("Expected Inchrory, Got: " + result);
		org.junit.Assert.assertEquals("The provided coordinates should in Inchrory", result, "Inchrory");
	}
	
	@Test
	public void NothingInTheMiddleOfNorthSea() {
		// the provided coordinates are in the middle of north sea.
		String result = p.webGetPlace(3.505735, 56.550987);
		System.out.println("Expected nothing, Got: " + result);
		org.junit.Assert.assertNull(result);
	}

}