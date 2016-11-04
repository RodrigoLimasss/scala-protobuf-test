import sbt._

object Dependencies{

  val commonDependencies : Seq[ModuleID] = Seq(
    "com.trueaccord.scalapb" %% "scalapb-runtime" % "0.5.42",
    "com.thenewmotion.akka"  %% "akka-rabbitmq"   % "2.3"
  )

  val dependencies : Seq[ModuleID] = commonDependencies
}