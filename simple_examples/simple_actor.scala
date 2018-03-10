package example

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props


class Worker extends Actor {
  def receive = {
    case x =>
      println(x)
  }
}

val system = ActorSystem("ExampleActorSystem")

val workerActorRef = system.actorOf(Props[Worker])
workerActorRef | "Hello World"
