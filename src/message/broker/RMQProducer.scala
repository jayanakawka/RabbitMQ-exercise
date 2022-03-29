package message.broker

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Connection
import com.rabbitmq.client.Channel


object RMQProducer extends App{

  val factory = new ConnectionFactory()
  factory.setHost("localhost")
  val connection = factory.newConnection()
  val channel = connection.createChannel()

  val queueName = "hello"
  // if the queue survives a server restart
  val durable = false
  // if the queue can only be used from the original connection
  val exclusive = false
  // if the queue should be deleted by the server when no longer needed
  val autoDelete = false
  // other arguments - not needed for now
  val arguments = null
  channel.queueDeclare(queueName, durable, exclusive, autoDelete, arguments)

  val message = "Hello world! "
  val exchange = "" // default exchange
  channel.basicPublish(exchange, queueName, null, message.getBytes)

  println(s"sent message $message")

  channel.close()
  connection.close()
}
