package com.yundai.playscala.playconcurrency.playatomic

import java.util.concurrent.atomic.AtomicLong
import com.yundai.playscala.playconcurrency.execute
/**
 * Created by yundai on 10/10/15.
 */
object AtomicUid extends App {
  private val uid = new AtomicLong(0L)
  def getUniqueId(): Long = uid.incrementAndGet()
  execute { println(s"Uid asynchronously: ${getUniqueId()}") }
  println(s"Got a unique id: ${getUniqueId()}")
}
