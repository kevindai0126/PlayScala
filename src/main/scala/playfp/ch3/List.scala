package playfp.ch3

import scala.annotation.tailrec

/**
 * Created by yundai on 4/3/17.
 */
sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x,xs) => x + sum(xs)
  }
  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x,xs) => x * product(xs)
  }
  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
  val example = Cons(1, Cons(2, Cons(3, Nil)))
  val example2 = List(1,2,3)
  val total = sum(example)

  def tail[A](list: List[A]): List[A] = {
    list match {
      case Nil => Nil
      case Cons(x, xs) => xs
    }
  }

  @tailrec
  def drop[A](l: List[A], n: Int): List[A] = {
    if(n == 0)
      l
    else {
      l match {
        case Nil => Nil
        case Cons(x, xs) => drop(xs, n - 1)
      }
    }
  }

  def dropWhile[A](l: List[A])(f: A => Boolean): List[A] = {
    l match {
      case Nil => Nil
      case Cons(x, xs) =>
        if(f(x))
          dropWhile(xs)(f)
        else
          Cons(x, dropWhile(xs)(f))
    }
  }

  def setHead[A](l: List[A], value: A): List[A] = {
    l match {
      case Nil => Nil
      case Cons(x, xs) => Cons(value, xs)
    }
  }

  def foldRight[A,B](l: List[A], z: B)(f: (A, B) => B): B =
    l match {
      case Nil => z
      case Cons(x, xs) => f(x, foldRight(xs, z)(f))
    }
  def sum2(l: List[Int]) =
    foldRight(l, 0.0)(_ + _)
  def product2(l: List[Double]) =
    foldRight(l, 1.0)(_ * _)

  def length[A](l: List[A]): Int = {
    foldRight(l, 0)((a: A, b :Int) => b + 1)
  }

  @tailrec
  def foldLeft[A,B](l: List[A], z: B)(f: (B, A) => B): B = {
    l match {
      case Nil => z
      case Cons(x, xs) => foldLeft(xs, f(z, x))(f)
    }
  }

  def sum2l(l: List[Int]) =
    foldLeft(l, 0.0)(_ + _)
  def product2l(l: List[Double]) =
    foldLeft(l, 1.0)(_ * _)
  def lengthl[A](l: List[A]): Int = {
    foldLeft(l, 0)((b :Int, a: A) => b + 1)
  }

  def reverse[A](l: List[A]): List[A] = {
    foldLeft[A, List[A]](l, Nil)((b: List[A], a: A) => Cons(a, b))
  }

  def foldLeftWithFoldRight[A,B](l: List[A], z: B)(f: (B, A) => B): B = {
    foldRight(l, z)((a: A, b: B) => f(b, a))
  }

  def foldRightWithFoldLeft[A,B](l: List[A], z: B)(f: (A, B) => B): B = {
    foldLeft(l, z)((b: B, a: A) => f(a, b))
  }

  def append[A](l1: List[A], l2: List[A]): List[A] = {
    foldRight(l1, l2)(Cons(_, _))
  }

  def concat[A](l : List[List[A]]): List[A] = {
    //foldLeft[List[A], List[A]](l, Nil)(append)
    foldRight[List[A], List[A]](l, Nil)(append)
  }

  def map[A,B](l: List[A])(f: A => B): List[B] = {
    l match {
      case Nil => Nil
      case Cons(x, xs) => Cons(f(x), map(xs)(f))
    }
  }

  def filter[A](l: List[A])(f: A => Boolean): List[A] = {
    l match {
      case Nil => Nil
      case Cons(x, xs) =>
        if(f(x))
          Cons(x, filter(xs)(f))
        else
          filter(xs)(f)
    }
  }

  def flatMap[A,B](l: List[A])(f: A => List[B]): List[B] = {
    l match {
      case Nil => Nil
      case Cons(x, xs) => append(f(x), flatMap(xs)(f))
    }
  }

  def filterWithFlatmap[A](l: List[A])(f: A => Boolean): List[A] = {
    def filter(a: A): List[A] = {
      if(f(a))
        Cons(a, Nil)
      else
        Nil
    }

    flatMap(l)(filter)
  }

  def addTwoLists(l1: List[Int], l2: List[Int]): List[Int] = {
    (l1, l2) match {
      case (Nil, Nil) => Nil
      case (Cons(x1, xs1), Cons(x2, xs2)) => Cons(x1 + x2, addTwoLists(xs1, xs2))
      case (Nil, Cons(x2, xs2)) => l2
      case (Cons(x1, xs1), Nil) => l1
    }
  }

  def operateTwoLists[A](l1: List[A], l2: List[A])(f: (A, A) => A): List[A] = {
    (l1, l2) match {
      case (Nil, Nil) => Nil
      case (Cons(x1, xs1), Cons(x2, xs2)) => Cons(f(x1, x2), operateTwoLists(xs1, xs2)(f))
      case (Nil, Cons(x2, xs2)) => l2
      case (Cons(x1, xs1), Nil) => l1
    }
  }
}
