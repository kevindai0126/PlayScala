package com.yundai.playscala.playconcurrency.playrx

import rx.lang.scala._
/**
 * Created by yundai on 10/12/15.
 */
object ObservablesExceptions extends App {
  val exc = new RuntimeException
  val o = Observable.items(1, 2) ++ Observable.error(exc) ++ Observable.items(3, 4)
  o.subscribe(
    x => println(s"number $x"),
    t => println(s"an error occurred: $t")
  )
}
