package playfp.ch5

import Stream._
/**
 * Created by yundai on 4/5/17.
 */
object Test extends App {

  val s = Stream(1,2,3,4,5)

  println(s.toList)
  println(s.take(2).toList)
  println(s.takeWhile(_ < 3).toList)

  println(s.map(_ +1).toList)
  println(s.filter(_ % 2 == 0).toList)

  println(constant(2).take(5).toList)
  println(from(1).take(5).toList)

  println(fibs().take(10).toList)

  println(s.zip(Stream(6,7,8,9,10)).toList)
  println(s.zipAll(Stream(6,7,8,9)).toList)
  println(s.zipAll(Stream(6,7,8,9,10,11)).toList)

  println(startsWith(s, Stream(1,2,3)))
  println(startsWith(s, Stream(1,2,4)))

  println(s.tails.map(_.toList).toList)

  println(hasSubsequence(s, Stream(2,3,4)))
  println(hasSubsequence(s, Stream(4,3)))

  println(s.scanRight(0)(_ + _).toList)
}
