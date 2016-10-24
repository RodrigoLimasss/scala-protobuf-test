import protobuf.person.Person

/**
  * Created by Rodrigo Lima on 21-Oct-16.
  */


object Main extends App {
  val p = Person()
    .withName("Rodrigo Lima")
    .withAge(27)
    .withAddresses(Seq("Wall Street", "55", "New York"))

  val bytes = p.toByteArray
  println("Bytes:" + bytes)

  val p1 = Person.parseFrom(bytes)
  println(p1)

}