package plugins

import java.io.FileDescriptor
import java.lang.reflect.Method
import java.security.Security

import sandbox1.shared.Plugin
/**
  * Created by adrian.bravo on 3/4/17.
  */
class Plugin4 extends Plugin {
  println("[PLUGIN] Initialising plugin")

  override def run(): Unit = {
    // Open Access to All Packages
    Security.setProperty("package.access", "")
    // Try to access a private sun.* package
    val klass = Class.forName("sun.nio.ch.IOUtil")
    println("[PLUGIN] Class = " + klass.getName)
    val mth: Method = klass.getMethod("newFD", 0.getClass)
    println("[PLUGIN] Method = " + mth)
    val fd = mth.invoke(null, 3.asInstanceOf[Object]).asInstanceOf[FileDescriptor]
    println("[PLUGIN] File Desc = " + fd)

    println("[PLUGIN] Plugin over")
  }
}