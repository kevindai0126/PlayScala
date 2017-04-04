package playfp.ch3

/**
 * Created by yundai on 4/4/17.
 */
sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {

  def size[A](tree: Tree[A]): Int = {
    tree match {
      case Leaf(v) => 1
      case Branch(l, r) => 1+ size(l) + size(r)
    }
  }

  def maximum(tree: Tree[Int]): Int = {
    tree match {
      case Leaf(v) => v
      case Branch(l, r) => size(l) max size(r)
    }
  }

  def depth[A](tree: Tree[A]): Int = {
    tree match {
      case Leaf(v) => 1
      case Branch(l, r) => (depth(l) max depth(r)) + 1
    }
  }

  def map[A, B](tree: Tree[A])(f: A=> B): Tree[B] = {
    tree match {
      case Leaf(v) => Leaf(f(v))
      case Branch(l, r) => Branch(map(l)(f), map(r)(f))
    }
  }

  def fold[A, B](tree: Tree[A])(f: A => B)(g: (B, B) => B): B = {
    tree match {
      case Leaf(v) => f(v)
      case Branch(l, r) => g(fold(l)(f)(g), fold(r)(f)(g))
    }
  }

  def sizeWithFold[A](tree: Tree[A]): Int = {
    fold(tree)(_ => 1)(1+ _ + _)
  }

  def maximumWithFold(tree: Tree[Int]): Int = {
    fold(tree)(a => a)((a, b) => a max b)
  }

  def depthWithFold[A](tree: Tree[A]): Int = {
    fold(tree)(_ => 1)((a, b) => 1 + (a max b))
  }

  def mapWithFold[A, B](tree: Tree[A])(f: A=> B): Tree[B] = {
    fold(tree)(a => Leaf(f(a)): Tree[B])(Branch(_, _))
  }

}
