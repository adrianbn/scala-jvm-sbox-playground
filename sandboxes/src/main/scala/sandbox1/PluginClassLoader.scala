package sandbox1

import java.net.{URL, URLClassLoader}
import scala.util.{Success, Try}

/**
  * Created by adrian.bravo on 2/5/17.
  */


class PluginClassLoader(urls: Array[URL]) extends URLClassLoader(urls) {

  // Child first class loader
  override def loadClass(name: String): Class[_] = {
    // check whether it has already been loaded
    findLoadedClass(name) match {
      case klass: Class[_] => klass // It had been loaded before, return it
      case _ => {
        // Not leaded before, try to do it ourselves (instead of using normal
        // parent delegation. This is child first.
        Try(findClass(name)) match {
          case Success(klass: Class[_]) => klass // Found it, return it
          case _ => super.loadClass(name) // If not found locally, use normal parent delegation in URLClassloader
          // It will throw ClassNotFoundException if can't find it
        }
      }
    }
  }
}

/*
Alternative pattern, rethink later.

trait PluginClassLoader extends URLClassLoader {

}

object PluginClassLoader {
  def apply(urls: Array[URL]) = new URLClassLoader(urls) with PluginClassLoader
}*/
