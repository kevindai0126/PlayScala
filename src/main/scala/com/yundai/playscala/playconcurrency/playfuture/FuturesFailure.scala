package com.yundai.playscala.playconcurrency.playfuture

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source
import scala.util.{Failure, Success, Try}

/**
 * Created by yundai on 10/10/15.
 */
object FuturesFailure extends App {
  val urlSpec: Future[Try[String]] = Future {
    val invalidUrl = "http://www.w3.org/Addressing/URL/url-spec.txt"
    //val invalidUrl = "http://www.w3.org/non-existent-url-spec.txt"
    Try(Source.fromURL(invalidUrl).mkString)
  }
  urlSpec onComplete {
    case Success(txt) => println(txt)
    case Failure(ex) => println(s"exception occurred - $ex")
  }
  Thread.sleep(1000)
}
