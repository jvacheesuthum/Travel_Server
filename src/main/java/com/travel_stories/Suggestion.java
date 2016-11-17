package com.travel_stories;

import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;

import com.google.gson.Gson;
import com.travel_stories.database.Place;
import com.travel_stories.database.ServerTimeLineEntry;
import com.travel_stories.database.TravelServerDatabase;

import scala.math.BigInt;

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
	
	public void addTimeLine(int user, String json) {
		Gson gson = new Gson();
		ServerTimeLineEntry[] entries = gson.fromJson(json, ServerTimeLineEntry[].class);
		for (ServerTimeLineEntry entry : entries) {
			db.storeTimeLineEntry(BigInt.javaBigInteger2bigInt(entry.location), entry.start, entry.end, user);
		}
	}
	
}
