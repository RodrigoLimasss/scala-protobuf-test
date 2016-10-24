import sbt.Keys._
import sbt._
import sbtprotoc.ProtocPlugin.autoImport.PB

object Build extends Build {

  val NamePrefix = "scala.protobuf.test"

  name := NamePrefix + "."

  lazy val wrapper = Project(
    id = "builder",
    base = file("builder")
  ).settings(Common.settings: _*)
    .settings(mainClass in Compile := Some("Main"))
    .settings(libraryDependencies ++= Dependencies.streamingDependencies)
    .settings(
      PB.targets in Compile := Seq(
        scalapb.gen() -> (sourceManaged in Compile).value
      )
    )
    .settings(PB.pythonExe := "C:\\Python27\\python.exe")

  fork in run := true
}