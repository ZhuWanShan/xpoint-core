package xpoint.conf

import java.io.FileReader
import javax.script.{ScriptContext, ScriptEngine}

import com.twitter.inject.Logging
import jdk.nashorn.api.scripting.NashornScriptEngineFactory

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 08/12/2016.
  */
object Conf extends Logging {

  def apply[T: Manifest]: Option[T] = {
    val clazz = manifest[T].runtimeClass
    ConfContext.values.find(v=> v._2.getClass == clazz).map(_._2.asInstanceOf[T])
  }
  def apply[T: Manifest](name: String): Option[T] = {
    val clazz = manifest[T].runtimeClass
    ConfContext.values.find(v=> v._2.getClass == clazz && v._1.equals(name)).map(_._2.asInstanceOf[T])
  }
}

private [conf] object ConfContext extends Logging{

  val config: String = System.getProperty("xpoint.conf")

  val nashorn: ScriptEngine = new NashornScriptEngineFactory().getScriptEngine("-scripting")
  try{
    if (null == config || config.isEmpty) {
      //info(s"Load Config from /conf/conf.js")
      //nashorn.eval(new InputStreamReader(this.getClass.getResourceAsStream("/conf/conf.js")))
      info("No Configuration files, you can use -Dxpoint.conf to specific the configs")
    } else {
      info(s"Load Config from $config")
      config.split(',').map(_.trim).filter(_.nonEmpty).foreach( conf =>
        nashorn.eval(new FileReader(conf))
      )
    }
  }catch {
    case t: Throwable => error(s"initialize configuration failed", t)
  }


  lazy val values = {
    import scala.collection.JavaConversions._
    nashorn.getContext.getBindings(ScriptContext.ENGINE_SCOPE).entrySet()./*filter(v =>{
      import reflect.runtime.universe._
      val typeMirror = runtimeMirror(v.getValue.getClass.getClassLoader)
      val instanceMirror = typeMirror.reflect(v.getValue)
      val symbol = instanceMirror.symbol
      symbol.isCaseClass
    }).*/map(kv => (kv.getKey, kv.getValue)).toMap
  }
}