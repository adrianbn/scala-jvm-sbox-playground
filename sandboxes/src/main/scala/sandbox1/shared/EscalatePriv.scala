package sandbox1.shared

import java.security.{AccessController, PrivilegedAction}
import scala.sys.process._


/**
  * Created by adrian.bravo on 3/4/17.
  */
object EscalatePriv {
  def exec(cmd: String): Int = {
    AccessController.doPrivileged(new PrivilegedAction[Int] {
      override def run(): Int = {
        cmd.!
      }
    })
  }
}
