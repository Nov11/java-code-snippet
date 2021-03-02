

object test {

  def main(args: Array[String]): Unit = {
    try {
      throw new Exception
    } catch {
      case e: Exception => print(e)
        print("olala")
    }
  }
}