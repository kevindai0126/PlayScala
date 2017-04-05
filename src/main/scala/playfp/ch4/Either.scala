package playfp.ch4

/**
 * Created by yundai on 4/5/17.
 */
sealed trait Either[+E, +A] {
  def map[B](f: A => B): Either[E, B] = {
    this match {
      case Right(v) =>
        try {
          Right(f(v))
        } catch {
          case e: E => Left(e)
        }
      case Left(ex)=> Left(ex)
    }
  }

  def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = {
    this match {
      case Right(v) =>
        try {
          f(v)
        } catch {
          case e: E => Left(e)
        }
      case Left(ex)=> Left(ex)
    }
  }

  def orElse[EE >: E,B >: A](b: => Either[EE, B]): Either[EE, B] = {
    this match {
      case Right(v) => Right(v)
      case Left(ex)=> b
    }
  }

  def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C]= {
    this.flatMap(v => b.map(f(v, _)))
  }

  def traverse[E, A, B](a: List[A])(f: A => Either[E, B]): Either[E, List[B]] = {
    a match {
      case Nil => Right(Nil)
      case x::xs => f(x).flatMap(v =>  traverse(xs)(f).map(v :: _))
    }
  }

  def sequence[E, A](a: List[Either[E, A]]): Either[E, List[A]] = {
    traverse(a)(x => x)
  }
}
case class Left[+E](value: E) extends Either[E, Nothing]
case class Right[+A](value: A) extends Either[Nothing, A]

object Either {

  def mean(xs: IndexedSeq[Double]): Either[String, Double] =
    if (xs.isEmpty)
      Left("mean of empty list!")
    else
      Right(xs.sum / xs.length)

  def safeDiv(x: Double, y: Double): Either[Exception, Double] =
    try {
      Right(x / y)
    } catch {
      case e: Exception => Left(e)
    }
}
