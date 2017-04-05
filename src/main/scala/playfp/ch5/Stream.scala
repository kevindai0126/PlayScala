package playfp.ch5

/**
 * Created by yundai on 4/5/17.
 */
trait Stream[+A] {
  def uncons: Option[(A, Stream[A])]
  def isEmpty: Boolean = uncons.isEmpty

  def toList: List[A] = {
    this.uncons match {
      case Some((v,s)) => v :: s.toList
      case None => Nil
    }
  }

  def take(n: Int): Stream[A] = {
    /*if(n == 0) {
      Stream.empty
    }
    else {
      uncons match {
        case Some((v,s)) => Stream.cons(v, s.take(n -1))
        case None => Stream.empty
      }
    }*/
    Stream.unfold((this, n))(S => {
      (S._1.uncons, S._2) match {
        case (Some(_), 0) => None
        case (Some((hd, tl)), r) => Some((hd, (tl, r - 1)))
        case _ => None
      }
    })
  }

  def takeWhile(p: A => Boolean): Stream[A] = {
    /*uncons match {
      case Some((v, s)) =>
        if(p(v))
          Stream.cons(v, s.takeWhile(p))
        else
          s.takeWhile(p)
      case None => Stream.empty
    }*/
    /*foldRight(Stream.empty[A])((hd, tl) =>
      if(p(hd))
        Stream.cons(hd, tl)
      else
        tl
    )*/
    Stream.unfold((this))(S => {
      S.uncons match {
        case Some((hd, tl)) if p(hd) =>
          Some((hd, tl.takeWhile(p)))
        case _ => None
      }
    })
  }

  def foldRight[B](z: => B)(f: (A, => B) => B): B =
    uncons match {
      case Some((h, t)) => f(h, t.foldRight(z)(f))
      case None => z
    }

  def exists(p: A => Boolean): Boolean =
    foldRight(false)((a, b) => p(a) || b)

  def forAll(p: A => Boolean): Boolean =
    foldRight(true)((a, b) => p(a) && b)

  def map[B](f: A => B): Stream[B] = {
    //foldRight(Stream.empty[B])((hd, tl) => Stream.cons(f(hd), tl))
    Stream.unfold(this)(S => {
      S.uncons match {
        case Some((hd, tl)) => Some(f(hd), tl)
        case _ => None
      }
    })
  }

  def filter(p: A => Boolean): Stream[A] = {
    foldRight(Stream.empty[A])((hd, tl) =>
      if(p(hd))
        tl
      else
        Stream.cons(hd, tl))
  }

  def zip[B](s2: Stream[B]): Stream[(A,B)] = {
    Stream.unfold(this, s2){ case (a1, a2) =>
      (a1.uncons, a2.uncons) match {
        case (Some((hd1, tl1)), Some((hd2, tl2))) =>
          Some((hd1, hd2), (tl1,tl2))
        case _ => None
      }
    }
  }

  def zipAll[B](s2: Stream[B]): Stream[(Option[A], Option[B])] = {
    Stream.unfold(this, s2){ case (a1, a2) =>
      (a1.uncons, a2.uncons) match {
        case (Some((hd1, tl1)), Some((hd2, tl2))) =>
          Some((Some(hd1), Some(hd2)), (tl1, tl2))
        case (Some((hd1, tl1)), None) =>
          Some((Some(hd1), None), (tl1, Stream.empty))
        case (None, Some((hd2, tl2))) =>
          Some((None, Some(hd2)), (Stream.empty, tl2))
        case (None, None) =>
          None
      }
    }
  }

  def tails: Stream[Stream[A]] = {
    Stream.unfold(this) ( S => {
      S.uncons match {
        case Some((hd, tl)) => Some(Stream.cons(hd, tl), tl)
        case None => None
      }
    })
  }

  def scanRight[B](z: => B)(f: (A, => B) => B): Stream[B] = {
    foldRight((z, Stream(z))){ case (a, b) =>
      lazy val c = b
      val res = f(a, c._1)
      (res, Stream.cons(res, c._2))
    }._2
  }
}

object Stream {
  def empty[A]: Stream[A] =
    new Stream[A] { def uncons = None }
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] =
    new Stream[A] {
      lazy val uncons = Some((hd, tl))
    }
  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty
    else cons(as.head, apply(as.tail: _*))

  def constant[A](a: A): Stream[A] = {
    //cons(a, constant(a))
    unfold(a)(s => Some(s, s))
  }

  def from(n: Int): Stream[Int] = {
    //cons(n, from(n + 1))
    unfold(n)(s => Some((s, s + 1)))
  }

  def fibs(): Stream[Int] = {
    /*def fib(a: Int, b: Int): Stream[Int] = {
      cons(a, fib(b, a + b))
    }

    fib(0,  1)*/
    unfold((0, 1))(s => Some(s._1, (s._2, s._1 + s._2)))
  }

  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = {
    f(z) match {
      case Some((v, s)) =>
        cons(v, unfold(s)(f))
      case None =>
        Stream.empty[A]
    }
  }

  def startsWith[A](s: Stream[A], s2: Stream[A]): Boolean = {
    s.zipAll(s2).takeWhile(_._2.nonEmpty).forAll {
      case (v1, v2) => v1 == v2
    }
  }

  def hasSubsequence[A](s1: Stream[A], s2: Stream[A]): Boolean =
    s1.tails exists (startsWith(_,s2))
}
