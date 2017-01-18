package com.travel_stories;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;

class ServerTimeLineEntry(photos:java.util.ArrayList[String],
                          locationName:String,
                          location:BigInteger,
                          start:GregorianCalendar,
                          end:GregorianCalendar,
                          lng:Double,
                          lat:Double) extends Serializable{




    def this(photos:java.util.ArrayList[String], locationName:String, location:BigInteger, start:GregorianCalendar, end:GregorianCalendar){
        this(photos, locationName, location, start, end, 0, 0)
    }

  def this(photos:java.util.ArrayList[String], locationName:String, start:GregorianCalendar, end:GregorianCalendar, lng:Double, lat:Double){
        this(photos, locationName, new BigInteger("0"), start, end, lng, lat)
    }    
    
}