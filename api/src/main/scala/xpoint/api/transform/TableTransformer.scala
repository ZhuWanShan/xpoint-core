package xpoint.api.transform

import xpoint.engine.script.domain.QueryResult

/**
  * Created by szhu.spark@gmail.com on 06/03/2017.
  */
object TableTransformer extends Transformer{
  override def format(rs: QueryResult, param: String*): Any = {
    val fields = rs.fields

    val cols = fields.map(f => TextColumn(f))

    val rows = rs.values

    Table(cols, rows.toSeq)
  }
}
