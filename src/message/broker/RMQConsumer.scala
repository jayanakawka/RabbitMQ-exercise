package message.broker

import com.rabbitmq.client.{CancelCallback, ConnectionFactory, DeliverCallback}

object RMQConsumer extends App{

  val QUEUE_NAME = "hello"

  val factory = new ConnectionFactory()
  factory.setHost("localhost")

  val connection = factory.newConnection()
  val channel = connection.createChannel()

  channel.queueDeclare(QUEUE_NAME, false, false, false, null)

  println(s"waiting for messages on $QUEUE_NAME Queue")

  val callback: DeliverCallback = (consumerTag, delivery) => {
    val message = new String(delivery.getBody, "UTF-8")
    println(s"Received $message")
  }

  val cancel: CancelCallback = consumerTag => {}

  // delete message from queue after successfully receive
  val autoAck = true
  channel.basicConsume(QUEUE_NAME, autoAck, callback, cancel)

  while(true) {
    // till message receive
    Thread.sleep(1000)
  }

  channel.close()
  connection.close()
}
