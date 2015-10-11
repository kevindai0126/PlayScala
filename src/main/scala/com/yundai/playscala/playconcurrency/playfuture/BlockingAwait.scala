package com.yundai.playscala.playconcurrency.playfuture

import scala.concurrent.{Await, Future}
import scala.io.Source
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * Created by yundai on 10/11/15.
 */
object BlockingAwait extends App {
  val urlSpecSizeFuture = Future {
    val specUrl = "http://www.w3.org/Addressing/URL/url-spec.txt"
    Source.fromURL(specUrl).size
  }
  val urlSpecSize = Await.result(urlSpecSizeFuture, 10.seconds)
  println(s"url spec contains $urlSpecSize characters")
}
