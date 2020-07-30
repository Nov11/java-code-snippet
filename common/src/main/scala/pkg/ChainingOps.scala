package pkg

object ChainingOps extends App {
  val list = 1 :: 13 :: 2 :: 2 :: 3 :: Nil
  val ret = list.map(x => (x, x)).sortWith(_._2 < _._2).map(x => x._1).mkString("#")
  print(ret)
}
