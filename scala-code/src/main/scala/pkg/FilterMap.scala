package pkg


object FilterMap {
  def main(args: Array[String]): Unit = {
    val m: Map[String, Int] = Map(("1", 1), ("2", 2), ("3", 3))

    val filtered = m.filter(k => k._2 > 2)
    print(filtered)
  }
}
