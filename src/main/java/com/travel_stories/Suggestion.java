package com.travel_stories;

import java.util.ArrayList;
import java.util.List;

import com.travel_stories.database.Place;
import com.travel_stories.database.TravelServerDatabase;

public class Suggestion {
	
    private TravelServerDatabase db;
    
    public Suggestion(TravelServerDatabase db) {
    	this.db = db;
    }
	
	public List<Place> placeSuggestions(double longitude, double latitude, int user) {
		List<Place> result = new ArrayList<Place>();
		try {
			Place[] dbresponse = db.nearbyPlace(longitude, latitude, user);
			for (int i = 0; i < dbresponse.length; i++) {
				if (dbresponse[i] != null) {
					result.add(dbresponse[i]);
				}
			}
			return result;
		} catch (Exception e) {
			return result;
		}
	}

}
