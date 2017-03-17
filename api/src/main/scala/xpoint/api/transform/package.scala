package xpoint.api

import xpoint.engine.script.domain.QueryResult

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 06/12/2016.
  */
package object transform {
  implicit def asTransfer(q: QueryResult): QueryResultTransfer = new QueryResultTransfer(q)
}
