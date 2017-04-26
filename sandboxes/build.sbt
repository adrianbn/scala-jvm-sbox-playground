scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.bouncycastle" % "bcprov-jdk16" % "1.46"
)

lazy val commonSettings = Seq(
  organization := "com.adrian",
  version := "1.0.0",
  name := "sandboxes"
)

lazy val `sandbox` = (project in file(".")).settings(
  commonSettings: _*
)