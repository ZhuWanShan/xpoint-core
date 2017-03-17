package xpoint.engine.script

import com.twitter.inject.Logging

/**
  * Created by szhu.spark@gmail.com on 13/03/2017.
  */
object ScriptLogging extends Logging{
  def d(log: Any): Unit = {
    debug(log)
  }
  def i(log: Any): Unit = {
    info(log)
  }
  def e(log: Any): Unit = {
    error(log)
  }
}
