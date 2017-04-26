package sandbox1

import java.io.FilePermission
import java.net.URL
import java.util.Enumeration
import java.security._

import collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

/**
  * Created by adrian.bravo on 2/9/17.
  */
class PluginPolicy extends Policy() {
  // Have to obtain our own PD here; will cause infinite recursion if done inside getPermissions
  private val pd = getClass.getProtectionDomain
  private val perms: PermissionCollection = new PermissionCollection {
    private val permissions = ArrayBuffer.empty[Permission]

    override def implies(permission: Permission): Boolean = {
      permissions.exists(_.implies(permission))
    }

    override def elements(): Enumeration[Permission] = permissions.toIterator.asJavaEnumeration

    override def add(permission: Permission): Unit = permissions += permission
  }

  private def addPermissions() = {
    perms.add(new FilePermission("/tmp", "read,write,delete"))
  }

  override def getPermissions(domain: ProtectionDomain): PermissionCollection = {
    println("PD: " + pd.getCodeSource)
    //println(domain)
    println(pd == domain)
    val p = new Permissions()
    p.add(new AllPermission())
    if (pd == domain) p
    else {
      // If the protection domain to be checked has the same code source as this class, no restrictions
      if (domain.getClassLoader.getClass == new ZeroPermClassLoader(Array.empty[URL]).getClass) {
        // super.getPermissions(domain)
        p
      }
      else
        perms
      //super.getPermissions(domain)
    }
  }

  override def getPermissions(codesource: CodeSource): PermissionCollection = {
    println(s"Codesource: ${codesource}")
    println(s"Code source's CL: ${codesource.getClass.getClassLoader}")
    // If the protection domain to be checked has the same code source as this class, no restrictions
    if (pd.getCodeSource == codesource) {
      //super.getPermissions(codesource)
      val p = new Permissions()
      p.add(new AllPermission())
      p
    } else
      //perms
      super.getPermissions(codesource)
  }

}
