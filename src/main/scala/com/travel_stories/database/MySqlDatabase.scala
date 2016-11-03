package com.travel_stories.database

/**
  * Created by jam414 on 24/10/16.
  */
class MySqlDatabase extends TravelServerDatabase {

  val dbConnection = new DatabaseConnection()
  dbConnection.connect

  override def getName(longitude: Double, latitude: Double): String = {
    
    val pkey:BigInt = mostPopular(cantor(longitude, latitude))
    
    val sb = new StringBuilder
    sb.append("SELECT `name` FROM `geonames` WHERE `pkey` = ")
    sb.append(pkey)
    sb.append(";")

    val query = sb.toString

    val result = dbConnection.retreiveQuery(query)

    if (result.isEmpty) throw LocationNotFoundException("Location not in db");
    else{
      //gross code
      result.head("name").asInstanceOf[String];
    }
  }
  
  override def storeName(name:String, longitude: Double, latitude: Double):Unit = {
    //TODO: 
    // try to getName
    // if empty just add
    // count
    // if 99, overwrite
    // else add to count
  }
  
  def cantor(longitude: Double, latitude: Double): BigInt = {
        // round the long and lat to 3 decimal points, accurate to 110m
    
    val lat:BigDecimal = BigDecimal(longitude * 1000).setScale(0, BigDecimal.RoundingMode.HALF_UP)
    val long:BigDecimal = BigDecimal(latitude * 1000).setScale(0, BigDecimal.RoundingMode.HALF_UP)
    
    // calculate the primary key 
    // using the cantor pairing function 0.5*(a+b)(a+b+1)+b
    
    val pk : BigDecimal = BigDecimal(0.5) * (long + lat) * (long + lat + BigDecimal(1)) + lat;
    
    return pk.setScale(0, BigDecimal.RoundingMode.FLOOR).toBigInt()
    
  }
  
  def mostPopular(key: BigInt): BigInt = {
    return key*100 +1
  }
  
}
