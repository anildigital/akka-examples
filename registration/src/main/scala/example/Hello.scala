package example

import scala.language.postfixOps
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import akka.actor.{ ActorRef, ActorSystem, Props, Actor }

import Storage._
import Recorder._
import Checker._


case class User(username: String, email: String)

object Recorder {
  sealed trait RecorderMsg
  case class NewUser(user: User) extends RecorderMsg
}

object Checker {
  sealed trait CheckerMsg
  // Checker messages
  case class CheckUser(user: User) extends CheckerMsg

  sealed trait CheckResponse
    // Checker responses
  case class BlackUser(user: User) extends CheckerMsg
  case class WhiteUser(user: User) extends CheckerMsg
}

object Storage {
  sealed trait StorageMsg
    // Storage messages

  case class AddUser(user: User) extends StorageMsg
}

class Storage extends Actor {
  var users = List.empty[User]

  def receive  = {
    case AddUser(user) =>
      users = user :: users
  }
}

class Checker extends Actor {

  def blacklist = List(
    User("Paul", "paul@gmail.com")
  )

  def receive = {
    case CheckUser(user) if blacklist.contains(user) =>
      println(s"Checker: $user is the blacklist")
      sender() ! BlackUser(user)
    case CheckUser(user) =>
      println(s"Checker: $user is the blacklist")
        sender() ! WhiteUser(user)
  }

}

class Recorder(checker: ActorRef, storage: ActorRef) extends Actor {
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val timeout = Timeout(5 seconds)

  def receive = {
    case NewUser(user) =>
      checker ? CheckUser(user) map {
        case WhiteUser(user) =>
          storage ! AddUser(user)
        case BlackUser(user) =>
            println(s"Recorder $user in the blacklist")
      }
  }
}

object TalkToActor extends App {

  // Create talk to actor system
  val system = ActorSystem("talk-to-actor")

  // Create the 'checker' actor
  val checker = system.actorOf(Props[Checker], "checker")

  // Create the storage actor
  val storage = system.actorOf(Props[Storage], "storage")

  // Creates the recorder message
  val recorder = system.actorOf(Props[Storage], "recorder")

  // Sends new message to recorder
  recorder ! Recorder.NewUser(User("Jon", "jon@gmail.com"))

  Thread.sleep(100)

  system.terminate()
}
