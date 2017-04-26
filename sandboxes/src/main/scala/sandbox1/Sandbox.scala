package sandbox1

import java.io.File
import java.net.{URL, URLClassLoader}
import java.security.Security
import java.util.{Calendar, Properties}

import org.bouncycastle.jce.provider.BouncyCastleProvider

/**
  * Created by adrian.bravo on 2/2/17.
  */
class Sandbox {
  private val properties = loadProperties

  private def loadProperties: Properties = {
    val prop = new Properties()
    val url: URL = ClassLoader.getSystemResource("sandbox1/conf/sandbox.properties")
    prop.load(url.openStream())
    prop
  }

  // Returns the class path for the current thread
  private def getClassPath: Vector[URL] = {
    Thread.currentThread().getContextClassLoader match {
      case cl: URLClassLoader => cl.getURLs.toVector
    }
  }

}

object Sandbox extends App {
  val sm = new SecurityManager
  System.setSecurityManager(sm)
  val pluginJar = new File(System.getProperty("user.home") + "/code/SandboxResearch/playground/plugins/target/scala-2.12/plugins_2.12-1.0.jar")

  //PluginLoader.loadPluginInNewThread(pluginJar)
  PluginLoader.loadPlugin(pluginJar)

}
