import protobuf.person.Person
import com.thenewmotion.akka.rabbitmq._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Rodrigo Lima on 21-Oct-16.
  */


object Main extends App {

  val factory = new ConnectionFactory()
  factory.setUsername("mbaffa")
  factory.setPassword("stone2016")
  factory.setHost("10.152.20.212")
  factory.setPort(5672)

  val connection: Connection = factory.newConnection()
  val channel: Channel = connection.createChannel()

  val exchangeName = "amq.direct"
  val queueName = "test_protobf"

  channel.queueDeclare(queueName, true, false, false, null)
  channel.queueBind(queueName, exchangeName, "")

  //Consumer
  Future {
    channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
      override def handleDelivery(consumerTag: String, envelope: Envelope, properties: BasicProperties, body: Array[Byte]) = {
        println(s"Receive: ${Person.parseFrom(body).name}")
      }
    })
  }

  //Publisher
  Future {
    (1 to 1000000).foreach { x =>
      val p = Person()
        .withName(s"Rodrigo Lima - $x")
        .withAge(x)
        .withAddresses(Seq("Wall Street", s"55$x", "New York"))

      channel.basicPublish(exchangeName, "", null, p.toByteArray)
      println(s"Sending: $x - ${p.name}")
    }
  }


}