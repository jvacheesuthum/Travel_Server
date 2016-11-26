package com.travel_stories;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.travel_stories.database.Place;
import com.travel_stories.ServerTimeLineEntry;
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
		System.out.println("in suggestion: addtimeline");
		Gson gson = new Gson();
		ServerTimeLineEntry[] entries = gson.fromJson(json, ServerTimeLineEntry[].class);
		System.out.println("gson parse survived");
		for (ServerTimeLineEntry entry : entries) {
			System.out.println("Entry: "+ entry.location.toString() + entry.start.toString() + entry.end.toString());
			db.storeTimeLineEntry(BigInt.javaBigInteger2bigInt(entry.location), entry.start.toString(), entry.end.toString(), user);
		}
	}
	
}
