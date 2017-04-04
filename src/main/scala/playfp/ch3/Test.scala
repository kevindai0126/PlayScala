package playfp.ch3

import List._
/**
 * Created by yundai on 4/3/17.
 */
object Test extends App{
    val l = List(1,2,3,4,5)
    val l2 = List(1.0,2.0,3.0,4.0,5.0)
    val ll = List(
      List(1,2),
      List(3,4),
      List(5,6)
    )

    println(l)
    println(tail(l))

    println(drop(l, 1))
    println(drop(l, 2))
    println(drop(l, 3))
    println(drop(l, 4))
    println(drop(l, 5))
    println(drop(l, 6))

    println(dropWhile(l)((x: Int) => x > 2))
    println(dropWhile(l)((x: Int) => x % 2 == 0))
    println(dropWhile(l)((x: Int) => x <= 0))

    println(setHead(l, 3))

    println(foldRight(List(1,2,3), Nil:List[Int])(Cons(_,_)))

    println(length(l))
    println(length(List(1,2,3)))

    println(sum2l(l))
    println(product2l(l2))
    println(lengthl(l))

    println(reverse(l))

    println(append(l, List(-1,-2,-3)))

    println(ll)
    println(concat(ll))

    println(map(l)(_ + 1))
    println(map(l2)(_.toString))

    println(filter(l)(_ % 2 == 0))

    println(flatMap(List(1,2,3))(i => List(i,i)))

    println(filterWithFlatmap(l)(_ % 2 == 0))

    println(addTwoLists(List(1,2,3), List(4,5,6)))

    val tree = Branch(Leaf(1), Branch(Leaf(2), Leaf(3)))
    println(Tree.size(tree))
    println(Tree.maximum(tree))
    println(Tree.depth(tree))
    println(Tree.map(tree)(_ + 1))

    println(Tree.sizeWithFold(tree))
    println(Tree.maximumWithFold(tree))
    println(Tree.depthWithFold(tree))
    println(Tree.mapWithFold(tree)(_ + 1))
}
