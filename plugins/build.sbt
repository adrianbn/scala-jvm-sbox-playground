name := "plugins"

version := "1.0"

scalaVersion := "2.12.1"

/*unmanagedJars in Compile ++= {
  val base = baseDirectory.value
  val baseDirectories = file("/Users/adrian.bravo/code/SandboxResearch/playground/sandboxes/target/scala-2.12/")
  val customJars = (baseDirectories ** "*.jar")
  customJars.classpath
}*/

libraryDependencies ++= Seq(
  "com.adrian" % "sandboxes_2.12" % "latest.integration" force()
)

