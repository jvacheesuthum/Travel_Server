package com.travel_stories.database

import java.util.GregorianCalendar;
import com.travel_stories.ServerTimeLineEntry
import scala.collection.mutable.ListBuffer

/**
  * Created by jam414 on 24/10/16.
  */
class MySqlDatabase extends TravelServerDatabase {
  
  // format of database:
  // | pkey | longitude | latitude | name | popularity |
  // each location pair (long lat) may have many names with varying popularity
  // pkey is formed by:
  // hashing long and lat to a unique value *100
  // last two decimal digits is the popularity ranking of this name at this location
  // '00' as the last two digits of pkey is the "index" of this location
  // the index's popularity value indicates number of names stored for this location.
  // max number of names stored at a location is 99. 
  // the lowest ranked is replaced by new entries
  // this is optimised so that retrieving the most popular name at a location is fast for db
  // only requires a lookup of pkey = hash(long, lat) * 100 +1

  val dbConnection = new DatabaseConnection()
  dbConnection.connect

  override def getName(longitude: Double, latitude: Double): Place = {
    
    val pkey:BigInt = mostPopular(toHash(longitude, latitude))
    
    val sb = new StringBuilder
    sb.append("SELECT * FROM `geonames` WHERE `pkey` = ")
    sb.append(pkey)
    sb.append(";")

    val query = sb.toString

    val result = dbConnection.retreiveQuery(query)

    if (result.isEmpty) throw LocationNotFoundException("Location not in db");
    else{
      println("found sth in db")
      //gross code      
      val name = result.head("name").asInstanceOf[String]
      println(name)
      println(result.head("popularity"))
      var p = new Place(pkey, name, latitude, longitude)
      p.setPopularity(Integer.parseUnsignedInt(result.head("popularity").toString()));
      println("finish db")
      return p
      
    }
  }
  
  override def storeName(name:String, longitude: Double, latitude: Double):Unit = {
    
    val key = toHash(longitude, latitude)
    
    // check index entry to see if location already has registered names
    var sb = new StringBuilder
    sb.append("SELECT `popularity` FROM `geonames` WHERE `pkey` = ").append(index(key)).append(";")
    var query = sb.toString
    val result = dbConnection.retreiveQuery(query)

    if (result.isEmpty) {
      // location does not exist, create new index first
      sb = new StringBuilder
      sb.append("INSERT INTO `geonames` (pkey, longitude, latitude, name, popularity) VALUES (")
      sb.append(index(key) + ", ").append(longitude + ", ").append(latitude + ", ").append("'index', ").append("1 );")
      query = sb.toString
      dbConnection.executeQuery(query);
      // add entry
      sb = new StringBuilder
      sb.append("INSERT INTO `geonames` (pkey, longitude, latitude, name, popularity) VALUES (")
      sb.append(entry(key, 1) + ", ").append(longitude + ", ").append(latitude + ", '").append(name + "', ").append("1 );")
      query = sb.toString
      dbConnection.executeQuery(query);
      
    } else {
      // the index has been found, check the number of existing entries.
      // the popularity field of an index stores the number of entries at the location
      val entries = result.head("popularity").asInstanceOf[Int]
      
      if (entries > 98) {
        //max number of entries at a location is 99, so replace the last entry
        sb = new StringBuilder
        sb.append("UPDATE `geonames` SET ")
        sb.append("longitude="+longitude).append(", latitude=" +latitude).append(", name='" + name)
        sb.append("' WHERE pkey=").append(entry(key, 99)).append(";")
        query = sb.toString
         dbConnection.executeQuery(query);
      } else {
        //increase num of entries in index (the popularity field of index is num)
        sb = new StringBuilder
        sb.append("UPDATE `geonames` SET popularity=").append(entries+1)
        sb.append(" WHERE pkey=").append(index(key)).append(";")
        query = sb.toString
        dbConnection.executeQuery(query);
        // add entry
        sb = new StringBuilder
        sb.append("INSERT INTO `geonames` (pkey, longitude, latitude, name, popularity) VALUES (")
        sb.append(entry(key, entries+1) + ", ").append(longitude + ", ").append(latitude + ", '").append(name + "', ").append("1 );")
        query = sb.toString
        dbConnection.executeQuery(query);
        
      }
      
    }
    
    // if empty just add
    // count
    // if 99, overwrite
    // else add to count
  }
  
  def toHash(longitude: Double, latitude: Double): BigInt = {
    // make long and lat positive. (range is +-180 and +-90 respectively)
    // round the long and lat to 3 decimal points, accurate to 110m
    
    val lat:BigDecimal = BigDecimal((longitude + 180) * 1000).setScale(0, BigDecimal.RoundingMode.HALF_UP)
    val long:BigDecimal = BigDecimal((latitude + 90) * 1000).setScale(0, BigDecimal.RoundingMode.HALF_UP)
    
    // calculate the primary key 
    // using the cantor pairing function 0.5*(a+b)(a+b+1)+b
    
    val pk : BigDecimal = BigDecimal(0.5) * (long + lat) * (long + lat + BigDecimal(1)) + lat;
    
    return pk.setScale(0, BigDecimal.RoundingMode.FLOOR).toBigInt()
    
  }
  
  def mostPopular(key: BigInt): BigInt = {
    return key*100 +1
  }
  
  def index(key: BigInt): BigInt = {
    return key*100
  }
  
  def entry(key: BigInt, rank: Int): BigInt = {
    assert(rank < 100)
    return key *100 + rank
   }
  
  def nearbyPlace(longitude: Double, latitude: Double, user:Int):Array[Place] ={
   
    val sb = new StringBuilder
    sb.append("SELECT * FROM geonames WHERE ABS(longitude-")
    sb.append(longitude)
    sb.append(") < 0.5 AND ABS(latitude-")
    sb.append(latitude)
    sb.append(") < 0.5 AND pkey NOT IN (SELECT location FROM TimeLineEntries WHERE user =")
    sb.append(user)
    sb.append(") ORDER BY popularity DESC;")

    val query = sb.toString

    val result = dbConnection.retreiveQuery(query)

    if (result.isEmpty) throw LocationNotFoundException("No suggestions Found");
    else{
      //gross code      
      var it = result.iterator
      var suggestions = new Array[Place](10)
      for (i <- 0 to 9) {
        if (it.hasNext) {
          var res = it.next()
          suggestions.update(i, new Place(res.get("pkey").asInstanceOf[BigInt], res.get("name").asInstanceOf[String], 
              res.get("latitude").asInstanceOf[Double], res.get("longitude").asInstanceOf[Double]))
        }
      }
      return suggestions
    }
  }
  
  def storeTimeLineEntry(location:BigInt, start:GregorianCalendar, end:GregorianCalendar, user:Int, trip:BigInt):Unit ={
    val key:BigInt = 100000000* user + start.getTimeInMillis/100000;
    val sb = new StringBuilder
    sb.append("INSERT INTO `TimeLineEntries` (pkey, location, start, end, user, trip) VALUES (")
    sb.append(key + ", ").append(`location` + ", ").append(start.getTimeInMillis/1000 + ", ").append(end.getTimeInMillis/1000 + ", ").append(user + ", ").append(trip + ");")
    val query = sb.toString
    dbConnection.executeQuery(query);
  }
  
  def storeTrip(user:Int, name:String, start:GregorianCalendar, end:GregorianCalendar):BigInt = {
    val key:BigInt = 100000000* user + start.getTimeInMillis/100000;
    val sb = new StringBuilder
    sb.append("INSERT INTO `Trips` (pkey, user, tripname, start, end) VALUES (")
    sb.append(key + ", ").append(user + ", ").append(name + ", ").append(start.getTimeInMillis/1000 + ", ").append(end.getTimeInMillis/1000 + ");")
    val query = sb.toString
    dbConnection.executeQuery(query);
    return key;
  }
  
  def getAllTrips(user:Int):List[List[ServerTimeLineEntry]] = {
    val result = dbConnection.retreiveQuery("SELECT * FROM Trips WHERE user = " + user + " ORDER BY pkey;")
    if (result.isEmpty) throw LocationNotFoundException("No timeline entries found for this trip");
    else {
      var trips = ListBuffer[List[ServerTimeLineEntry]]()
      var it = result.iterator
      while(it.hasNext) {
        var res = it.next()
        trips.+=(getTrip(res.get("pkey").asInstanceOf[BigInt]))
      }
      return trips.toList
    }
  }
  
  def getTrip(trip:BigInt):List[ServerTimeLineEntry] ={
    val sb = new StringBuilder
    sb.append("SELECT geonames.name, geonames.pkey AS location, TimeLineEntries.start, TimeLineEntries.end")
    sb.append("FROM TimeLineEntries JOIN geonames ON TimeLineEntries.location = geonames.pkey WHERE TimeLineEntries.trip = ")
    sb.append(trip)
    sb.append(" ORDER BY TimeLineEntries.start;") 
    val query = sb.toString

    val result = dbConnection.retreiveQuery(query)
    if (result.isEmpty) throw LocationNotFoundException("No timeline entries found for this trip");
    else {
      var trip = new ListBuffer[ServerTimeLineEntry]()
      var it = result.iterator;
      while(it.hasNext) {
        var res = it.next()
        val startcal:GregorianCalendar = new GregorianCalendar()
        startcal.setTimeInMillis(res.get("start").asInstanceOf[Long]*1000)
        val endcal:GregorianCalendar = new GregorianCalendar()
        endcal.setTimeInMillis(res.get("end").asInstanceOf[Long]*1000)
        trip.+=(new ServerTimeLineEntry(null, res.get("name").asInstanceOf[String], res.get("location").asInstanceOf[java.math.BigInteger], startcal, endcal));
      }
      return trip.toList;
    }    
  }

  
}
