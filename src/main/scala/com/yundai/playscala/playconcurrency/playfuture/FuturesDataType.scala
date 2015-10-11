package com.yundai.playscala.playconcurrency.playfuture

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source
/**
 * Created by yundai on 10/10/15.
 */
object FuturesDataType extends App {
  val buildFile: Future[String] = Future {
    val f = Source.fromFile("build.sbt")
    try f.getLines.mkString("\n") finally f.close()
  }
  println(s"started reading the build file asynchronously")
  println(s"status: ${buildFile.isCompleted}")
  Thread.sleep(250)
  println(s"status: ${buildFile.isCompleted}")
  println(s"value: ${buildFile.value}")
}

