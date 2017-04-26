package plugins

import java.net.{URL, URLClassLoader}

import sandbox1.shared.Plugin
import scala.sys.process._

/**
  * Created by adrian.bravo on 2/3/17.
  */
class Plugin1 extends Plugin {
  println("[PLUGIN] Initialising plugin")
  // "open /Applications/Calculator.app/".!

  def run(): Unit = {
    println(s"[PLUGIN] ProtectionDomain: ${getClass.getProtectionDomain}")
    val klass: Class[_] = getClass
    //val classpath: Vector[URL] = Thread.currentThread().getContextClassLoader match {
    val classpath: Vector[URL] = klass.getClassLoader match {
    //val classpath: Vector[URL] = ClassLoader.getSystemClassLoader match {
      case cl: URLClassLoader => cl.getURLs.toVector
      case cl: ClassLoader => cl.asInstanceOf[URLClassLoader].getURLs.toVector
    }
    println(s"[PLUGIN] Plugin ClassLoader: ${klass.getClassLoader}")

    println("[PLUGIN] Plugin classpath includes: ")
    classpath.foreach(u => println(s"[PLUGIN] $u"))
    println("[PLUGIN] Hello from plugin land")
    println("[PLUGIN] Plugin over")
  }
}
