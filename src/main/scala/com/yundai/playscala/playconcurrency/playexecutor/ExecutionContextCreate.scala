package com.yundai.playscala.playconcurrency.playexecutor

import scala.concurrent.ExecutionContext
import scala.concurrent.forkjoin.ForkJoinPool

/**
 * Created by yundai on 10/10/15.
 */
object ExecutionContextCreate extends App {

  val pool = new ForkJoinPool(2)
  val etx = ExecutionContext.fromExecutorService(pool)
  etx.execute(new Runnable {
    override def run(): Unit = println("This task is run asynchronously.")
  })
  Thread.sleep(500)
}
