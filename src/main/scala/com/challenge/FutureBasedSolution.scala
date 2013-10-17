package com.challenge

import scala.concurrent.Await
import scala.concurrent.duration._
import dispatch._
import Defaults._
import Util._

object FutureBasedSolution extends App {
    val urls = readUrlsFromInputFile(args)

    startTime()

    // Request text files in parallel using futures
    val futures = urls.map { txtUrl =>
      Http(url(txtUrl) OK as.String)
    }

    // Convert the future text into future frequencies
    val futureFrequencies = futures.map { future =>
      future.map(txt => calculateFrequencies(txt.split(' ')))
    }

    // Combine Future Frequencies
    val resultFuture = Future.sequence(futureFrequencies).map(combineMaps)
    // Wait for computation to complete
    val result = Await.result(resultFuture, 1 minutes)
    // Write Frequencies to file
    writeFrequencies(result)
    // stop timer
    stopTime()
    System.exit(0)
}