package example

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props


// Define actor messages
case class WhoToGreet(who: String)

// Define Greet actor
class Greeter extends Actor {
  def receive = {
    case WhoToGreet(who) => println(s"Hello $who")
  }
}

object Hello extends Greeting with App {
  val system = ActorSystem("Hello-Akka")

  val greeter = system.actorOf(Props[Greeter], "greeter")

  greeter ! WhoToGreet("Anil")
}

trait Greeting {
  lazy val greeting: String = "hello"
}
