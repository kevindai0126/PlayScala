package com.yundai.playscala

import scala.concurrent.ExecutionContext

/**
 * Created by yundai on 10/10/15.
 */
package object playconcurrency {

  def execute(body: => Unit) =
  ExecutionContext.global.execute(
    new Runnable {
      override def run() = body
    }
  )
}
