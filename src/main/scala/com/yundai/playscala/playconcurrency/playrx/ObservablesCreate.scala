package com.yundai.playscala.playconcurrency.playrx

import rx.lang.scala._
/**
 * Created by yundai on 11/6/15.
 */
object ObservablesCreate extends App {
  val vms = Observable.create[String] { obs =>
    obs.onNext("JVM")
    obs.onNext("DartVM")
    obs.onNext("V8")
    obs.onCompleted()
    Subscription()
  }
  vms.subscribe(println _, e => println(s"oops - $e"), () => println("Done!"))
}
