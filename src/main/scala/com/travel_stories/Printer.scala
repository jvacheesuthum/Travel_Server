package com.travel_stories

/**
  * Created by jam414 on 19/10/16.
  */
class Printer(var words:String) {

  def print() =  System.out.println(words);

  override def toString: String = words;

}
