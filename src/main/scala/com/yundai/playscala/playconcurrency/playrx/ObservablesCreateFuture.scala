package com.yundai.playscala.playconcurrency.playrx

import rx.lang.scala._
import scala.concurrent._
import ExecutionContext.Implicits.global
/**
 * Created by yundai on 11/6/15.
 */
object ObservablesCreateFuture extends App {
  val f = Future { "Back to the Future(s)" }
  val o = Observable.create[String] { obs =>
    f foreach { case s => obs.onNext(s); obs.onCompleted() }
    f.failed foreach { case t => obs.onError(t) }
    Subscription()
  }
  o.subscribe(println(_))
}
