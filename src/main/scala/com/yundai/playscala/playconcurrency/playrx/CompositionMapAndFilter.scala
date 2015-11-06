package com.yundai.playscala.playconcurrency.playrx

import scala.concurrent.duration._
import rx.lang.scala._
/**
 * Created by yundai on 11/6/15.
 */
object CompositionMapAndFilter extends App {
  val odds = Observable.interval(0.5.seconds)
    .filter(_ % 2 == 1).map(n => s"num $n").take(5)
  odds.subscribe(
    println _, e => println(s"unexpected $e"), () => println("no more odds"))
  Thread.sleep(4000)
}
