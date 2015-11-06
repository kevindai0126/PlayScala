package com.yundai.playscala.playconcurrency.playrx

import rx.lang.scala._
import scala.concurrent.duration._
/**
 * Created by yundai on 10/12/15.
 */
object ObservablesTimer extends App {
  val o = Observable.timer(1.second)
  o.subscribe(_ => println("Timeout!"))
  o.subscribe(_ => println("Another timeout!"))
  Thread.sleep(2000)
}