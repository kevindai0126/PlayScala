package com.yundai.playscala.playconcurrency.playexecutor

import scala.concurrent.forkjoin.ForkJoinPool

/**
 * Created by yundai on 10/10/15.
 */
object ExecutorCreate extends App {

  val executor = new ForkJoinPool()
  executor.execute(new Runnable {
    override def run(): Unit = println("This task is run asynchronously.")
  })
  Thread.sleep(500)
}
