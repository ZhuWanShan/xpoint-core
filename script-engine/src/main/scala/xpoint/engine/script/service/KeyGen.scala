package xpoint.engine.script.service

import java.security.MessageDigest

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 09/01/2017.
  */
object KeyGen {
  val digest: MessageDigest = MessageDigest.getInstance("SHA-1")

  def hash(str: String): String = {
    digest.digest(str.getBytes("UTF8")).map("%02X" format _).mkString
  }
}
