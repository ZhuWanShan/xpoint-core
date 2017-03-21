package xpoint.server

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import xpoint.api._
import xpoint.api.exception.RootExceptionMapper
import xpoint.api.service.TokenService
import xpoint.engine.script.ScriptEngine
import xpoint.engine.script.service.ScriptHook


/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 09/01/2017.
  */
class ApiServer()
               (
                 implicit val tokenService: TokenService,
                 implicit val scriptEngine: ScriptEngine,
                 implicit val scriptHook: ScriptHook
               ) extends HttpServer{

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
//      .add(new ScriptCrud(injector.instance[MessageBodyManager]))
//      .add(new ScriptFileUpload)
      .add(new ScriptDataSource)
//      .add(new ScriptDebug)
//      .add(new ScriptRegister)
//      .add(new ChartSource)
      .exceptionMapper[RootExceptionMapper]
  }
}
