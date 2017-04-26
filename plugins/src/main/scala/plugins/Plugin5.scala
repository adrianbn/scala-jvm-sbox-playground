package plugins

import java.beans.Expression

import sandbox1.shared.Plugin
import sun.misc.Unsafe
import sun.reflect.misc.{FieldUtil, MethodUtil}

/**
  * Created by adrian.bravo on 3/5/17.
  */
class Plugin5 extends Plugin {
  println("[PLUGIN] Initialising plugin")

  override def run(): Unit = {
    val unsafeField = FieldUtil.getDeclaredFields(Class.forName("java.nio.Bits")).find(_.getName == "unsafe").get
    val unsafe = unsafeField.getType
    val mthd = MethodUtil.getMethods(Class.forName("sun.misc.Unsafe"))
    println(mthd)

    val expr = new Expression(Class.forName("sun.misc.Unsafe"), "getUnsafe", Array.empty)

    expr.execute()
    println(expr.getValue)
    val hasAllPermField = FieldUtil.getDeclaredFields(getClass.getProtectionDomain.getClass).find(_.getName == "hasAllPerm").get
    //unsafe.staticFieldBase(hasAllPermField)
    println("[PLUGIN] Plugin over")
  }
}
