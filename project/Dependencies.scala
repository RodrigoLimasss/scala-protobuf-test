import sbt._

object Dependencies{

  val commonDependencies : Seq[ModuleID] = Seq(
    "com.trueaccord.scalapb" %% "scalapb-runtime" % "0.5.42"
  )

  val streamingDependencies : Seq[ModuleID] = commonDependencies
}