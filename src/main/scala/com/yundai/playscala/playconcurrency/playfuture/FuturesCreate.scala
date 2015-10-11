package com.yundai.playscala.playconcurrency.playfuture

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * Created by yundai on 10/10/15.
 */
object FuturesCreate extends App {
  Future { println("Future is here")}
  println("Future is coming")
  Thread.sleep(100)
}
