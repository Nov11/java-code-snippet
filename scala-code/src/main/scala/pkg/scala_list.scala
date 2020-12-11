package pkg

import scala.collection.mutable

object scala_list {
  def main(args: Array[String]): Unit = {
    val result: mutable.MutableList[(String, String)] = mutable.MutableList[(String, String)]()
//    result.add()
    result.+=(("1", "2"))
  }
}
