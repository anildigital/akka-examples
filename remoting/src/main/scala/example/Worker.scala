package example

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class Worker extends Actor {
  import Worker._

  def receive = {
    case msg: Work =>
        println(s"I received work message and ActorRef: ${self}")
  }
}

object Worker {
  case class Work(message: String)
}
