import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.5.11",
      "com.typesafe.akka" %% "akka-remote" % "2.5.11",
      "com.typesafe.akka" %% "akka-cluster" % "2.5.11",
      "com.typesafe.akka" %% "akka-persistence" % "2.5.11",
      "com.typesafe.akka" %% "akka-contrib" % "2.5.11",
      scalaTest % Test
    )
  )
