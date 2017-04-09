package playfp.ch6

/**
 * Created by yundai on 4/9/17.
 */
sealed trait Input
case object Coin extends Input
case object Turn extends Input
case class Machine(locked: Boolean, candies: Int, coins: Int)

object Candy {
  import State._

  def update = (i: Input) => (s: Machine) =>
    (i, s) match {
      case (_, Machine(_, 0, _)) => s
      case (Coin, Machine(true, candies, coins)) => Machine(false, candies, coins + 1)
      case (Turn, Machine(false, candies, coins)) => Machine(true, candies - 1, coins)
      case (Turn, Machine(true, _, _)) => s
      case (Coin, Machine(false, _, _)) => s
    }

  def simulateMachine(inputs: List[Input]): State[Machine, Int] =
    for {
      _ <- sequence(inputs.map(modify[Machine] _ compose update))
      s <- get
    } yield s.coins

}
