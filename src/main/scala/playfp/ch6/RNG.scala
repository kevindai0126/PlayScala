package playfp.ch6

/**
 * Created by yundai on 4/8/17.
 */
trait RNG {
  def nextInt: (Int, RNG)
}

object RNG {
  type Rand[+A] = RNG => (A, RNG)

  def simple(seed: Long): RNG = new RNG {
    def nextInt = {
      val seed2 = (seed * 0x5DEECE66DL + 0xBL) &
        ((1L << 48) - 1)
      ((seed2 >>> 16).asInstanceOf[Int],
        simple(seed2))
    }
  }

  def positiveInt(rng: RNG): (Int, RNG) = {
    val (i, r) = rng.nextInt
    (if (i < 0) -i else i, r)
  }

  def double(rng: RNG): (Double, RNG) = {
    val (i, r) = positiveInt(rng)

    (i / (Int.MaxValue.toDouble + 1), r)
  }

  def intDouble(rng: RNG): ((Int, Double), RNG) = {
    val (i, r1) = rng.nextInt
    val (d, r2) = double(r1)

    ((i, d), r2)
  }

  def doubleInt(rng: RNG): ((Double, Int), RNG) = {
    val ((i, d), r) = intDouble(rng)

    ((d, i), r)
  }

  def double3(rng: RNG): ((Double, Double, Double), RNG) = {
    val (d1, r1) = double(rng)
    val (d2, r2) = double(r1)
    val (d3, r3) = double(r2)

    ((d1, d2, d3), r3)
  }

  def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
    if (count == 0) {
      (List(), rng)
    }
    else {
      val (i, r1) = rng.nextInt

      val (s, r2) = ints(count - 1)(r1)

      (i :: s, r2)
    }
  }

  val int: Rand[Int] = _.nextInt

  def unit[A](a: A): Rand[A] =
    rng => (a, rng)

  def map[A, B](s: Rand[A])(f: A => B): Rand[B] = {
    /*rng => {
      val (a, rng2) = s(rng)
      (f(a), rng2)
    }*/
    flatMap(s)(x => unit(f(x)))
}

  def positiveMax(n: Int): Rand[Int] = {
    map(positiveInt)(_ % (n + 1))
  }

  def double2: Rand[Double] = {
    map(positiveInt)(_ / (Int.MaxValue.toDouble + 1))
  }

  def map2[A,B,C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = {
    /*rng => {
      val (a, r1) = ra(rng)
      val (b, r2) = rb(r1)

      (f(a,b), r2)
    }*/

    flatMap(ra)(a => map(rb)(f(a, _)))
  }

  def intDouble2: Rand[(Int,Double)] = {
    map2(positiveInt, double)((_, _))
  }

  def doubleInt2: Rand[(Double,Int)] = {
    map2(double, positiveInt)((_, _))
  }

  def sequence[A](fs: List[Rand[A]]): Rand[List[A]] = {
    fs.foldRight(unit(List[A]()))((f1, f2) => map2(f1, f2)(_ :: _))
  }

  def ints2(count: Int): Rand[List[Int]] = {
    sequence(List.fill(count)(int))
  }

   def flatMap[A,B](f: Rand[A])(g: A => Rand[B]): Rand[B] = {
     rng => {
       val (a, r1) = f(rng)

       g(a)(r1)
     }
   }

  def positiveInt2: Rand[Int] = {
    flatMap(int)(x => {
      if(x != Int.MinValue)
        unit(Math.abs(x))
      else
        positiveInt
    })
  }
}