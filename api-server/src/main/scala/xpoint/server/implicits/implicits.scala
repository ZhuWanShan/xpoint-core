package xpoint.server

import javax.sql.DataSource

import com.twitter.util.FuturePool
import xpoint.api.callback.ExecutorPool
import xpoint.api.service.TokenService
import xpoint.dataaccess.datasource.PrestoDataSource
import xpoint.engine.script.ScriptEngine
import xpoint.engine.script.service.{ScriptHook, SqlService}

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 03/01/2017.
  */
package object implicits {
  //implicit val jdbcContext = JdbcContext

 // implicit val mysqlDataSource = MysqlDataSource()

  implicit val defaultCallbackPool = ExecutorPool.callbackPool

  implicit val prestoDataSource = PrestoDataSource()

  implicit val defaultSqlService = new SqlService {
    override val ds: DataSource = prestoDataSource
  }

  implicit val tokenService = new TokenService {}

  implicit val defaultCallbackHook = new ScriptHook {
    override val callbackPool: FuturePool = defaultCallbackPool
  }

  //implicit val chartScript = new ChartScript {}

  implicit val scriptEngine = new ScriptEngine {
    override val sql: SqlService = defaultSqlService
    override val hook: ScriptHook = defaultCallbackHook
  }

}
