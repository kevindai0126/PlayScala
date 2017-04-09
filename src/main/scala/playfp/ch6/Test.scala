package playfp.ch6

/**
 * Created by yundai on 4/8/17.
 */
object Test extends App {

  import RNG._
  import Candy._

  val rng = simple(100)
  val (i1, r1) = positiveInt(rng)
  val (i2, r2) = positiveInt(r1)

  println(i1)
  println(i2)

  val (d1, r3) = double(r2)
  val (d2, r4) = double(r3)

  println(d1)
  println(d2)

  println(intDouble(rng)._1)
  println(doubleInt(rng)._1)
  println(double3(rng)._1)
  println(ints(10)(rng)._1)

  println(positiveMax(10)(rng))
  println(double2(rng))
  println(intDouble2(rng))
  println(doubleInt2(rng))
  println(ints2(10)(rng))

  println(positiveInt2(rng))
  println(positiveInt2(r1))

  println(simulateMachine(List(Coin, Coin, Coin, Coin)).run(Machine(true, 10, 10)))
}
