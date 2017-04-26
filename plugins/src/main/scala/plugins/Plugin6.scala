package plugins

import sandbox1.shared.Plugin

/**
  * Created by adrian.bravo on 3/5/17.
  */
class Plugin6 extends Plugin {
  println("[PLUGIN] Initialising plugin")

  override def run(): Unit = {
    val klass = Class.forName("sandbox1.shared.NewInstanceDemo")
    println("[PLUGIN] Class = " + klass.getName)
    //klass.newInstance()

    println("[PLUGIN] Plugin over")
  }
}
