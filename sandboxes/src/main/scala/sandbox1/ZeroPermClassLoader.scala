package sandbox1

import java.net.URL
import java.security._
import sun.misc.URLClassPath

/**
  * Created by adrian.bravo on 2/5/17.
  */
class ZeroPermClassLoader(urls: Array[URL]) extends PluginClassLoader(urls) {
  val ucp = new URLClassPath(urls)


  /*@throws[IOException]
  private def defineClass(name: String, res: Resource): Class[_] = {
    val t0: Long = System.nanoTime
    val i: Int = name.lastIndexOf('.')
    val url: URL = res.getCodeSourceURL
    if (i != -1) {
      val pkgname: String = name.substring(0, i)
      // Check if package already loaded.
      val man: Manifest = res.getManifest
      definePackageInternal(pkgname, man, url)
    }
    // Now read the class bytes and define the class
    val bb: ByteBuffer = res.getByteBuffer
    if (bb != null) {
      // Use (direct) ByteBuffer:
      val signers: Array[CodeSigner] = res.getCodeSigners
      val cs: CodeSource = new CodeSource(url, signers)
      sun.misc.PerfCounter.getReadClassBytesTime.addElapsedTimeFrom(t0)
      return defineClass(name, bb, cs)
    }
    else {
      val b: Array[Byte] = res.getBytes
      // must read certificates AFTER reading bytes.
      val signers: Array[CodeSigner] = res.getCodeSigners
      val cs: CodeSource = new CodeSource(url, signers)
      sun.misc.PerfCounter.getReadClassBytesTime.addElapsedTimeFrom(t0)
      return defineClass(name, b, 0, b.length, cs)
    }
  }*/

  override protected def findClass(name: String): Class[_] = {
    println(s"[SBOX] Loading class: $name")

    // Load the resource
    val path = name.replace('.', '/').concat(".class")
    val res = ucp.getResource(path, false)
    // Define a protection domain with no permissions
    val permissions = new Permissions()
    val codeSource = new CodeSource(res.getCodeSourceURL, res.getCodeSigners)
    val protectionDomain = new ProtectionDomain(codeSource, permissions)
    val bytes = res.getBytes
    defineClass(name, bytes, 0, bytes.length, protectionDomain)
  }
}
