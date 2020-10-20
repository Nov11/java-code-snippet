package pkg

object MapUsingAnnoyFunction {
  def f(x: Int): Int = {
    if (x > 1) {
      return 10
    }
    return 11
  }

  def main(args: Array[String]): Unit = {
    val a = List(1, 2, 3)
    val ff = (x: Int) => {
      if (x > 1) {
        return 1
      }
      return 0
    }:Int
        val b: Set[Int] = a.map((x:Int)=>{
          if(x > 1){ x}
          else  2
        }).toSet
//    val b: Set[Int] = a.map(ff).toSet
    b.foreach(x => println(x))
//    val output = ff(11)
//    println("ll", output)
  }
}
