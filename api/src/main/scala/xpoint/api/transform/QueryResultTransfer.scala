package xpoint.api.transform

import com.twitter.inject.Logging
import xpoint.engine.script.domain.QueryResult

import scala.util.matching.Regex

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 06/12/2016.
  */
class QueryResultTransfer(rs: QueryResult) extends Logging {
  val FUNC_PATTERN: Regex = """(\w+)\(((\w+,?)*)\)""".r

  def format(format: String): Any = {
    format match {
      case FUNC_PATTERN(name,param,_) =>
        val p = if(param.length > 0){
          param.split(',').toSeq
        }else{
          Seq()
        }
        Transforms.get(name).format(rs, p: _*)
      case _ =>
        Transforms.default.format(rs)
    }
  }
}

