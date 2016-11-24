package com.travel_stories;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.travel_stories.database.LocationNotFoundException;
import com.travel_stories.database.Place;
import com.travel_stories.database.TravelServerDatabase;

public class PlaceFinder {

    boolean verbose = false;

    private TravelServerDatabase db;
    private String key;
    private final int[] radius = {10, 100, 500, 1000};

    public PlaceFinder(TravelServerDatabase db, String apikey) {
        this.db = db;
        this.key = apikey;
    }

    public PlaceFinder(TravelServerDatabase db, String apikey, boolean verbosity) {
        this.db = db;
        this.key = apikey;
        this.verbose = verbosity;
    }

    public String getPlace(Double longitude, Double latitude) {
        
    	String result;
        
        try {
            //try to find from stored data
        	if (verbose) System.out.println("searching in db");
            Place p = db.getName(longitude, latitude);
            return p.getName();
        } catch (LocationNotFoundException e) {
        	if (verbose) System.out.println("not found in db");
            // if data is not found in db, search google maps
            result = webGetPlace(longitude, latitude);
        }
        return storeInDB(result, longitude, latitude);
    }

    String webGetPlace(Double longitude, Double latitude) {

        // filter controls whether we should find "interesting" places or any place
        boolean filter = true;

        // try to search the more exact location, then expand radius is nothing is found.
        for (int i =0; i< 4;) {

            if (verbose) System.out.println("searching for radius: " + radius[i]);
            if (verbose && !filter) System.out.println("Interesting place filter is off");

            // create search from given long lat, with varying radius and filter options
            StringBuilder sb = new StringBuilder();
            sb.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=");
            sb.append(latitude);
            sb.append(',');
            sb.append(longitude);
            sb.append("&radius=");
            sb.append(radius[i]);
            if (filter) {
                sb.append("&type=point_of_interest&rankby=prominence&key=");
            } else {
                sb.append("&rankby=prominence&key=");
            }
            sb.append(key);

            try {
                // request and parse the json response from google map api
                URL apirequest = new URL(sb.toString());
                JsonParser jp = new JsonParser();
                JsonObject apiresponse = jp.parse(new InputStreamReader((InputStream) apirequest.getContent())).getAsJsonObject();
                if (apiresponse.get("status").getAsString().equals("OK")) {
                    return apiresponse.getAsJsonArray("results").get(0).getAsJsonObject().get("name").getAsString();
                } else {
                    // expand radius and/or turn off filter
                    if (i == 3 && filter) filter = !filter;
                    else i++;
                }

            } catch (JsonIOException | JsonSyntaxException | IOException e) {
                System.out.println("Error in api request or result");
                return null;
            }

        }

        return null;

    }
    
    private String storeInDB(String result, Double longitude, Double latitude) {
    	if (result != null) {
    		db.storeName(result, longitude, latitude);
    	}
    	return result;
    }

}