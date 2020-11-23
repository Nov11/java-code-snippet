package pkg

object fififi {
  def main(args: Array[String]): Unit = {
    val a = 1
    val b = if (a > 0) {
      -11
    } else {
      11
    }

    print(b)
  }
}
