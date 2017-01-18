package com.travel_stories;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.travel_stories.database.Place;
import com.travel_stories.database.TravelServerDatabase;

import scala.math.BigInt;

public class Suggestion {
	
    private TravelServerDatabase db;
    
    public Suggestion(TravelServerDatabase db) {
    	this.db = db;
    }
	
	public List<Place> placeSuggestions(double longitude, double latitude, BigInt user) {
		System.out.println("Suggestion: suggestions");
		List<Place> result = new ArrayList<Place>();
		try {
			Place[] dbresponse = db.nearbyPlace(longitude, latitude, user);
			for (int i = 0; i < dbresponse.length; i++) {
				if (dbresponse[i] != null) {
					System.out.println("suggestion java: " + dbresponse[i].getName());
					result.add(dbresponse[i]);
				}
			}
			return result;
		} catch (Exception e) {
			System.out.println("Exception in suggestion java:" + e.getMessage());
			return result;
		}
	}
	
	public BigInt addTimeLine(BigInt user, BigInt tripkey, String json) {
		System.out.println("in suggestion: addtimeline");
		Gson gson = new Gson();
		ServerTimeLineEntryJava[] entries = gson.fromJson(json, ServerTimeLineEntryJava[].class);
		System.out.println("gson parse survived");
		if (entries.length > 0) {
			db.updateTrip(tripkey, "default name", entries[0].start, entries[entries.length-1].end);
			for (ServerTimeLineEntryJava entry : entries) {
				BigInt key;
				if (entry.location.equals(new BigInteger("0"))) {
					Place userentry = db.storeName(entry.locationName, entry.lng, entry.lat);
					key = db.storeTimeLineEntry(BigInt.javaBigInteger2bigInt(userentry.getKey()), entry.start, entry.end, user, tripkey);
				} else {
					System.out.println("Entry: "+ entry.location.toString() + entry.start.getTimeInMillis() + entry.end.getTimeInMillis());
					key = db.storeTimeLineEntry(BigInt.javaBigInteger2bigInt(entry.location), entry.start, entry.end, user, tripkey);
				}
				for (String path: entry.photos) {
					db.linkPhoto(key, path);
				}
			}
			return tripkey;
		}
		return null;
	}
	
}
