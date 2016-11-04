package settings

import akka.actor.ActorSystem
import com.rabbitmq.client.ConnectionFactory
import com.thenewmotion.akka.rabbitmq._

/**
  * Created by rlsilva on 04/11/2016.
  */

trait ServiceSettings

object ServiceSettings extends ServiceSettings {

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

  implicit val actorSystem = ActorSystem()
}