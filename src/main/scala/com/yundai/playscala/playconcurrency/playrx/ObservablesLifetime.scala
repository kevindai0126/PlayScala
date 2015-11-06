package com.yundai.playscala.playconcurrency.playrx

import rx.lang.scala._
/**
 * Created by yundai on 11/6/15.
 */
object ObservablesLifetime extends App {
  val classics = List("Good, bad, ugly", "Titanic", "Die Hard")
  val movies = Observable.from(classics)
  movies.subscribe(new Observer[String] {
    override def onNext(m: String) = println(s"Movies Watchlist - $m")
    override def onError(e: Throwable) = println(s"Ooops - $e!")
    override def onCompleted() = println(s"No more movies.")
  })
}
