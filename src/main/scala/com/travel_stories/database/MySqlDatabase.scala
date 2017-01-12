package com.travel_stories.database

import java.util.GregorianCalendar;
import com.travel_stories.ServerTimeLineEntry
import scala.collection.mutable.ListBuffer
import java.math.BigInteger

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
    print("database getname")
    
    val pkey:BigInt = mostPopular(toHash(longitude, latitude))
    
    val sb = new StringBuilder
    sb.append("SELECT * FROM `geonames` WHERE `pkey` = ")
    sb.append(pkey)
    sb.append(";")

    val query = sb.toString
    print("sending query")
    val result = dbConnection.retreiveQuery(query)
    print("after query")
    if (result.isEmpty) throw LocationNotFoundException("Location not in db");
    else{
      println("found sth in db")
      //gross code      
      val name = result.head("name").asInstanceOf[String]
      println(name)
      println(result.head("popularity"))
      var p = new Place(new BigInteger(pkey.toString()), name, latitude, longitude)
      p.setPopularity(Integer.parseUnsignedInt(result.head("popularity").toString()));
      println("finish db")
      return p
      
    }
  }
  
  override def getLocation(name:String):Place = {
    print("database getLocation")
    
    val sb = new StringBuilder
    sb.append("SELECT * FROM `geonames` WHERE `name` LIKE \'")
    sb.append(name)
    sb.append("\' ORDER BY popularity;")

    val query = sb.toString
    print("sending query")
    val result = dbConnection.retreiveQuery(query)
    print("after query")
    if (result.isEmpty) throw LocationNotFoundException("Location not in db");
    else{
      println("found sth in db")
      //gross code      
      val long = result.head("longitude").asInstanceOf[Double]
      val lat = result.head("latitude").asInstanceOf[Double]
      val pkey = result.head("pkey").asInstanceOf[BigInteger]
      var p = new Place(pkey, name, lat, long)
      p.setPopularity(Integer.parseUnsignedInt(result.head("popularity").toString()));
      println("finish db")
      return p
      
    }    
  }
  
  override def storeName(name:String, longitude: Double, latitude: Double):Place = {
    
    val key = toHash(longitude, latitude)
    var pkey:BigInt = 0
    
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
      pkey = entry(key, 1)
      sb = new StringBuilder
      sb.append("INSERT INTO `geonames` (pkey, longitude, latitude, name, popularity) VALUES (")
      sb.append(pkey + ", ").append(longitude + ", ").append(latitude + ", '").append(name + "', ").append("1 );")
      query = sb.toString
      dbConnection.executeQuery(query);
      
    } else {
      // the index has been found, check the number of existing entries.
      // the popularity field of an index stores the number of entries at the location
      val entries = result.head("popularity").asInstanceOf[Int]
      
      if (entries > 98) {
        //max number of entries at a location is 99, so replace the last entry
        pkey = entry(key, 99)
        sb = new StringBuilder
        sb.append("UPDATE `geonames` SET ")
        sb.append("longitude="+longitude).append(", latitude=" +latitude).append(", name='" + name)
        sb.append("' WHERE pkey=").append(pkey).append(";")
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
        pkey = entry(key, entries+1)
        sb = new StringBuilder
        sb.append("INSERT INTO `geonames` (pkey, longitude, latitude, name, popularity) VALUES (")
        sb.append(pkey + ", ").append(longitude + ", ").append(latitude + ", '").append(name + "', ").append("1 );")
        query = sb.toString
        dbConnection.executeQuery(query);

      }
    }
    
    val place = new Place(new BigInteger(pkey.toString()), name, latitude, longitude)
    place.setPopularity(1)
    return place
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
  
  def nearbyPlace(longitude: Double, latitude: Double, user:BigInt):Array[Place] ={
    println("database: nearbyPlace")
   
    val sb = new StringBuilder
    sb.append("SELECT * FROM geonames WHERE name <> 'index ' AND ABS(longitude-")
    sb.append(longitude)
    sb.append(") < 5 AND ABS(latitude-")
    sb.append(latitude)
    sb.append(") < 5 AND pkey NOT IN (SELECT location FROM TimeLineEntries WHERE user =")
    sb.append(user)
    sb.append(") ORDER BY popularity DESC;")

    val query = sb.toString
    println("query")
    val result = dbConnection.retreiveQuery(query)
    println("after query")
    if (result.isEmpty) throw LocationNotFoundException("No suggestions Found");
    else{
      println("result is not empty")
      //gross code      
      var it = result.iterator
      println("iterator ok")
      var suggestions = new Array[Place](10)
      println("array ok")
      for (i <- 0 to 9) {
        if (it.hasNext) {
          var res = it.next()
          println("Suggestion: " + res.get("name").get.asInstanceOf[String])
          suggestions.update(i, new Place(res.get("pkey").get.asInstanceOf[BigInteger], res.get("name").get.asInstanceOf[String], 
              res.get("latitude").get.asInstanceOf[Double], res.get("longitude").get.asInstanceOf[Double]))
        }
      }
      return suggestions
    }
  }
  
  def storeTimeLineEntry(location:BigInt, start:GregorianCalendar, end:GregorianCalendar, user:BigInt, trip:BigInt):BigInt ={
    println("db store timeline entry")
    val sb = new StringBuilder
    sb.append("INSERT INTO `TimeLineEntries` (location, start, end, user, trip) VALUES (")
    sb.append(`location` + ", ").append(start.getTimeInMillis/1000 + ", ").append(end.getTimeInMillis/1000 + ", ").append(user + ", ").append(trip + ");")
    val query = sb.toString
    dbConnection.executeQuery(query);
    println("write done")
    val result = dbConnection.retreiveQuery("SELECT LAST_INSERT_ID() FROM TimeLineEntries;")
    println("asked for key")
    val key:BigInt = new BigInteger(result.head.get("LAST_INSERT_ID()").get.toString())
    println("db stored timeline entry: DONE: key: " + key)
    return key;
  }
  
  def updateTrip(tripkey:BigInt, name:String, start:GregorianCalendar, end:GregorianCalendar):Unit = {
    println("db update trip")  
    val sb = new StringBuilder
    sb.append("UPDATE `Trips` SET tripname=")
    sb.append("\'" + name + "\', start=").append(start.getTimeInMillis/1000 + ", end=").append(end.getTimeInMillis/1000)
    sb.append("WHERE pkey=" + tripkey + ";")
    val query = sb.toString
    dbConnection.executeQuery(query);
    println("update done")
  }
  
  def storeTrip(user:BigInt):BigInt = {
    val query = "INSERT INTO `Trips` (user, tripname, start, end) VALUES(" + user +  ", 'undefined', 0, 0);"
    dbConnection.executeQuery(query);
    val result = dbConnection.retreiveQuery("SELECT LAST_INSERT_ID() FROM Trips;")
    println("asked for key")
    if (!result.isEmpty) println("have something: " + result.head.mkString(","))
    val key:BigInt = new BigInteger(result.head.get("LAST_INSERT_ID()").get.toString())
    println("db stored trip: DONE: key: " + key)
    return key;
  }
  
  def getAllTrips(user:BigInt):List[List[ServerTimeLineEntry]] = {
    val result = dbConnection.retreiveQuery("SELECT * FROM Trips WHERE user = " + user + " ORDER BY pkey;")
    if (result.isEmpty) throw LocationNotFoundException("No timeline entries found for this trip");
    else {
      var trips = ListBuffer[List[ServerTimeLineEntry]]()
      var it = result.iterator
      while(it.hasNext) {
        var res = it.next()
        trips.+=(getTrip(res.get("pkey").get.asInstanceOf[BigInteger]))
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
        startcal.setTimeInMillis(res.get("start").get.asInstanceOf[Long]*1000)
        val endcal:GregorianCalendar = new GregorianCalendar()
        endcal.setTimeInMillis(res.get("end").get.asInstanceOf[Long]*1000)
        trip.+=(new ServerTimeLineEntry(null, res.get("name").get.asInstanceOf[String], res.get("location").get.asInstanceOf[java.math.BigInteger], startcal, endcal));
      }
      return trip.toList;
    }    
  }
  
  def storeTrace(tripkey:BigInt, time:Int, long:Double, lat:Double):Unit = {
    val sb = new StringBuilder
    sb.append("INSERT INTO `Traces` (time, longitude, latitude, trip) VALUES (")
    sb.append(time + ", ").append(long + ", ").append(lat + ", ").append(tripkey + ");")
    val query = sb.toString
    dbConnection.executeQuery(query);
    println("db stored trace entry: DONE")
 
  }
  
  def linkPhoto(tlentry:BigInt, path:String):Unit = {
    val sb = new StringBuilder
    sb.append("INSERT INTO `Photo` (tlentry, path) VALUES (")
    sb.append(tlentry + ", '").append(path + "');")
    dbConnection.executeQuery(sb.toString())
    println("db linked photo")
  }


  
}
