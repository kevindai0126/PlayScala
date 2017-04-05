package playfp.ch4

import java.util.regex._

/**
 * Created by yundai on 4/5/17.
 */
sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = {
    this match {
      case None => None
      case Some(v) => Some(f(v))
    }
  }
  def flatMap[B](f: A => Option[B]): Option[B] = {
    this match {
      case None => None
      case Some(v) => f(v)
    }
  }
  def getOrElse[B >: A](default: => B): B = {
    this match {
      case None => default
      case Some(v) => v
    }
  }
  def orElse[B >: A](ob: => Option[B]): Option[B] = {
    this match {
      case None => ob
      case Some(v) => this
    }
  }
  def filter(f: A => Boolean): Option[A] = {
    this match {
      case None => None
      case Some(v) => if(f(v)) this else None
    }
  }
}

case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]

object Option {

  def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)

  def variance(xs: Seq[Double]): Option[Double] = {
    mean(xs).flatMap(m => mean(xs.map(x => math.pow(x - m, 2))))
  }

  def lift[A,B](f: A => B): Option[A] => Option[B] = _ map f

  def pattern(s: String): Option[Pattern] =
    try {
      Some(Pattern.compile(s))
    } catch {
      case e: PatternSyntaxException => None
    }

  def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = {
    for {
      v1 <- a
      v2 <- b
    } yield f(v1, v2)
  }

  def mkMatcher_1(pat: String): Option[String => Boolean] =
    for {
      p <- pattern(pat)
    } yield ((s: String) => p.matcher(s).matches)


  def bothMatch_2(pat1: String, pat2: String, s: String): Option[Boolean] = {
    map2(mkMatcher_1(pat1), mkMatcher_1(pat2))((f, g) => f(s) && g(s))
  }

  def sequence[A](a: List[Option[A]]): Option[List[A]] = {
    /*a match {
      case Nil => Some(Nil)
      case x::xs => x.flatMap(v =>  sequence(xs).map(v :: _))
    }*/
    traverse(a)(x => x)
  }

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = {
    a match {
      case Nil => Some(Nil)
      case x::xs => f(x).flatMap(v =>  traverse(xs)(f).map(v :: _))
    }
  }
}
