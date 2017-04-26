package sandbox1

import java.io.File
import java.net.{URL, URLClassLoader}

import sandbox1.shared.Plugin

import scala.util.Try

/**
  * Created by adrian.bravo on 2/3/17.
  */
object PluginLoader {
  val entryClass = "plugins.Plugin6"

  // WARNING: Loading plugins this way exposes the ContextClassLoader of the Sandbox and therefore its classpath
  def loadPlugin(jar: File): Unit = {
    val pcl = new PluginClassLoader(Array(jar.toURI.toURL))
    val plugin = pcl.loadClass(entryClass).newInstance().asInstanceOf[Plugin]
    plugin.run()
  }

  def loadPluginInNewThread(jar: File): Unit = {
    //println(s"[SBOX] PluginLoader PD: ${getClass.getProtectionDomain}")
    val pcl = new PluginClassLoader(Array(jar.toURI.toURL))
    val plugin = pcl.loadClass(entryClass).newInstance().asInstanceOf[Plugin]

    //val klass = pcl.loadClass(entryClass)
    //val method = klass.getDeclaredMethod(entryMethod)
    // This is transferring the privileges of this class down to the constructor of the Plugin
    // If I provide an interface they must abide by I can cast the loaded class and instantiate
    // normally without breaking the security boundary.
    //val instance = klass.newInstance()
    /* Corolary:
      There may be two different ClassLoaders in scope;
      The ClassLoader for the class, and the ClassLoader for the context of the Current Thread
      To fully isolate a plugin, it must be loaded by a different classloader and run in its own
      thread.
      By default a new Thread inherits its parent ContextClassLoader. To avoid this CL being accessed by
      the plugin, set the CCL of the thread to the CL of the plugin itself.
      Note: The plugin could get a hold of the System ClassLoader via ClassLoader.getSystemClassloader()
      unless disallowed via Policy.
     */
    val thread = new Thread( () => {
      def run() = {
        plugin.run()
      }
    })
    thread.setContextClassLoader(pcl)
    thread.start()
    thread.join()
    //Try(thread.start())
  }
}
