package xpoint.engine.script

import java.util
import javax.script._

import com.twitter.inject.Logging
import jdk.nashorn.api.scripting.NashornScriptEngineFactory
import xpoint.conf.Conf
import xpoint.engine.script.service.SqlService

import scala.io.Source

/**
  * Created by szhu.spark@gmail.com on 13/03/2017.
  */
trait ScriptEngine extends Logging{

  val nashorn: javax.script.ScriptEngine = new NashornScriptEngineFactory().getScriptEngine("-scripting")

  val sql: SqlService


  val SYSTEM = new util.HashMap[String, Object]()
  val ROOT: String = Conf[String]("xpoint_script_location")
    .getOrElse(throw new IllegalArgumentException(s"Missing Config xpoint_script_location"))

  protected[script] lazy val context: javax.script.ScriptContext = {
    val ctx = new SimpleScriptContext()
    val engineScope = new SimpleBindings()
    engineScope.put("$", SYSTEM)
    ctx.setBindings(engineScope, ScriptContext.GLOBAL_SCOPE)
    ctx
  }

  def initScripts(): Unit ={
    val xpoint = Source.fromURL(s"$ROOT/ds/xpoint.js").getLines().mkString("\n")
    nashorn.eval(xpoint, context)
  }

  def initSystem(): Unit ={
    SYSTEM.put("Log", ScriptLogging)
    SYSTEM.put("SQL", sql)
    SYSTEM.put("ROOT", ROOT)
  }

  def exist(name: String): Boolean = {
    context.getAttribute(name) != null
  }

  protected[script] lazy val engine: javax.script.ScriptEngine = {
    initSystem()
    nashorn.setContext(context)
    initScripts()
    nashorn
  }

  def cleanContext(): Unit ={
    val engineScope = context.getBindings(ScriptContext.ENGINE_SCOPE)
    if(null != engineScope){
      engineScope.clear()
    }
    initScripts()
  }

  def execute(ctx: Object, function: String, params: Seq[String] = Seq()): Any = {
    val ctl = new util.HashMap[String, Object]()
    ctl.put("ctx", ctx)
    ctl.put("lock", new Lock())
    engine.asInstanceOf[Invocable].invokeFunction("xpoint_execute", function, ctl, params.toArray)
  }
}
