package com.yundai.playscala.playconcurrency.playexecutor

import scala.concurrent.ExecutionContext

/**
 * Created by yundai on 10/10/15.
 */
object ExecutionContextGlobal extends App{

  val etx = ExecutionContext.global
  etx.execute(new Runnable {
    override def run(): Unit = println("This task is run asynchronously.")
  })
  Thread.sleep(500)
}
