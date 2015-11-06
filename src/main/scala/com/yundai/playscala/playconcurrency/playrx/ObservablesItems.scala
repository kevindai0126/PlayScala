package com.yundai.playscala.playconcurrency.playrx

import rx.lang.scala._
/**
 * Created by yundai on 10/12/15.
 */
object ObservablesItems extends App {
  val o = Observable.items("Pascal", "Java", "Scala")
  o.subscribe(name => println(s"learned the $name language"))
  o.subscribe(name => println(s"forgot the $name language"))
}
