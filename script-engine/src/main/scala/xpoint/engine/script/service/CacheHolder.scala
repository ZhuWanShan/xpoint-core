package xpoint.engine.script.service

import java.util.concurrent.TimeUnit

import com.google.common.cache.{Cache, CacheBuilder}
import xpoint.engine.script.domain.QueryResult

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 09/01/2017.
  */
object CacheHolder {
  val cache: Cache[String, QueryResult] = CacheBuilder
    .newBuilder()
    .maximumSize(20000)
    //.concurrencyLevel()
    .expireAfterAccess(10, TimeUnit.MINUTES)
    .build[String, QueryResult]()
}
