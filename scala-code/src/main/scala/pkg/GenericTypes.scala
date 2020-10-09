package pkg

class GenericTypes {

}

trait Thing

class Vehicle extends Thing

class Car extends Vehicle

class Jeep extends Vehicle

class Coupe extends Car

class Motorcycle extends Vehicle

class Bicycle extends Vehicle

class Tricycle extends Vehicle

class Parking[A >: Bicycle <: Vehicle](val plaza: A)

object GenericTypes {
  def main(args: Array[String]): Unit = {
    val v1 = new Parking(new Bicycle)
    val v2 = new Parking(new Vehicle)
    val v3 = new Parking(new Jeep) //Parking[Vehicle]

  }
}