package pkg

final case class DumbCaseClass(i: Int) {

  if (i != 0) {
    throw new IllegalArgumentException("blabla")
  }
}

object Drive {
  def main(args: Array[String]): Unit = {
    DumbCaseClass(1)
  }
}
