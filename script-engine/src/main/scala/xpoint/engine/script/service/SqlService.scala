package xpoint.engine.script.service

import java.util.concurrent.Callable
import javax.sql.DataSource

import com.twitter.inject.Logging
import xpoint.engine.script.domain.QueryResult

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 02/12/2016.
  */
trait SqlService extends Logging{
  val ds: DataSource

  def query(sql: String, cache: Boolean): QueryResult = {
    if(cache){
      cacheOrQuery(sql)
    }else{
      execute(sql)
    }
  }


  def query(sql: String): QueryResult = {
    query(sql, cache = true)
  }

  protected [script] def execute(sql: String): QueryResult = {
    val conn = ds.getConnection
    try {
      val rs = conn.createStatement().executeQuery(sql)
      val meta = rs.getMetaData
      val fields = (1 to meta.getColumnCount).map(i =>
        if (null == meta.getColumnLabel(i))
          meta.getColumnName(i)
        else
          meta.getColumnLabel(i)
      )

      val values = scala.collection.mutable.ArrayBuffer[Array[Any]]()

      while (rs.next()) {
        values +=
          (1 to meta.getColumnCount).map(i =>
            rs.getObject(i)
          ).toArray
      }

      QueryResult(fields.toArray, values.toArray)

    } finally {
      conn.close()
    }
  }
  protected [script] def cacheOrQuery(sql: String): QueryResult = {
    val cacheKey = KeyGen.hash(sql)
    CacheHolder.cache.get(cacheKey, new Callable[QueryResult] {
      override def call(): QueryResult = {
        debug(s"Refresh Cache for [$sql]")
        execute(sql)
      }
    })
  }
}
