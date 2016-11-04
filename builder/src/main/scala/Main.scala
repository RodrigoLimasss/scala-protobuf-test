import akka.actor.{Props, Actor}
import protobuf.person.Person
import com.thenewmotion.akka.rabbitmq._

/**
  * Created by Rodrigo Lima on 21-Oct-16.
  */


object Main extends App {

  import settings.ServiceSettings._

  //Consumer
  actorSystem.actorOf(Props(new Actor {
    override def preStart: Unit = {
      channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
        override def handleDelivery(consumerTag: String, envelope: Envelope, properties: BasicProperties, body: Array[Byte]) = {
          self ! Person.parseFrom(body)
        }
      })
    }

    override def receive: Receive = {
      case person: Person => println(s"Receive: ${person.name}")
    }

  }), "consumer-actor")


  //Publisher
  actorSystem.actorOf(Props(new Actor {
    override def preStart: Unit = {
      (1 to 1000000).foreach { x =>
        self ! Person()
          .withName(s"Rodrigo Lima - $x")
          .withAge(x)
          .withAddresses(Seq("Wall Street", s"55$x", "New York"))
      }
    }

    override def receive: Receive = {
      case p: Person =>
        channel.basicPublish(exchangeName, "", null, p.toByteArray)
        println(s"Sending: ${p.name}")
    }

  }), "publisher-actor")


}