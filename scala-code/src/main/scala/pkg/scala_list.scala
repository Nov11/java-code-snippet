package pkg

import scala.collection.mutable

object scala_list {
  def main(args: Array[String]): Unit = {
    val result: mutable.MutableList[(String, String)] = new mutable.MutableList[(String, String)]()
    //result.add(("1", "2"))
    result.+=(("1", "2"))
    print(result)
  }
}
