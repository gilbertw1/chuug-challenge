package com.challenge

import java.io.{File,FileWriter}
import scala.io.Source
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.collection.mutable

object Util {

  var stime = 0L

  def startTime() {
    stime = System.currentTimeMillis
  }

  def stopTime() {
    println(s"Run Time: ${System.currentTimeMillis - stime}")
  }

  def readUrlsFromInputFile(args: Array[String]): Iterable[String] = {
    inputSource(args).getLines.toList
  }

  def inputSource(args: Array[String]): Source = {
    if (args.length > 0)
      Source.fromFile(args(0))
    else
      Source.fromURL(getClass.getResource("/test-urls.txt"))
  }

  def calculateFrequencies(words: Array[String]): mutable.Map[String,Int] = calculateFrequencies(words, 0, words.length)

  def calculateFrequencies(words: Array[String], start: Int, end: Int): mutable.Map[String,Int] = {
    val freqs = mutable.Map[String,Int]()
    var idx = start
    while(idx < end) {
      freqs(words(idx)) = freqs.getOrElseUpdate(words(idx), 0) + 1
      idx += 1
    }
    freqs
  }

  def combineMaps(maps: Iterable[mutable.Map[String,Int]]): mutable.Map[String,Int] = {
    maps.reduce { (m, s) =>
      s.keys.foreach { k =>
        if(m contains k)
          m(k) = m(k) + s(k)
        else
          m(k) = s(k)
      }
      m
    }
  }

  def writeFrequencies(frequencies: mutable.Map[String,Int]) {
    val fw = new FileWriter("frequencies.txt", true)
    try {
      frequencies.foreach { case (word,freq) =>
        fw.write(s"${word}: ${freq}\n")
      }
    }
    finally fw.close() 
  }
}