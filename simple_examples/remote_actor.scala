val workerActorRef = system.actorOf(Props[Worker])

workerActorRef ! "Hello Conference"

val workerActorRef =
  context.actorSelection("akka:tcp://ExampleActorSystem@127.0.0.1:9005/usr/WorkerActor")

workerActorRef ! "Hello World"
