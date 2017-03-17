package xpoint.api.transform

import xpoint.engine.script.domain.QueryResult

/**
  * Created by szhu.spark@gmail.com on 06/03/2017.
  */
trait Transformer {
  def format(rs: QueryResult, param: String *): Any
}
