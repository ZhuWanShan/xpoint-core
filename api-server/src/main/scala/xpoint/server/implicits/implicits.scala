package xpoint.server

import javax.sql.DataSource

import xpoint.api.service.TokenService
import xpoint.dataaccess.datasource.PrestoDataSource
import xpoint.engine.script.ScriptEngine
import xpoint.engine.script.service.SqlService

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 03/01/2017.
  */
package object implicits {
  //implicit val jdbcContext = JdbcContext

 // implicit val mysqlDataSource = MysqlDataSource()

  implicit val prestoDataSource = PrestoDataSource()

  implicit val defaultSqlService = new SqlService {
    override val ds: DataSource = prestoDataSource
  }

  implicit val tokenService = new TokenService {}

  //implicit val chartScript = new ChartScript {}

  implicit val scriptEngine = new ScriptEngine {
    override val sql: SqlService = defaultSqlService
  }

}
