package xpoint.api

import java.util.Date

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import xpoint.api.callback.ExecutorPool._
import xpoint.api.service.TokenService
import xpoint.api.transform._
import xpoint.engine.script.domain.QueryResult
import xpoint.engine.script.{ScriptEngine, ScriptScope}

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 09/01/2017.
  */
class ScriptDataSource()(implicit val scriptQuery: ScriptEngine, val tokenService: TokenService) extends Controller {

  get("/data/test") { req: Request =>
    response.ok("OK").contentType("text/html")
  }

  get("/cleanContext") { req: Request =>
    scriptQuery.cleanContext()
    response.ok("OK").contentType("text/html")
  }

  any("/data/:target"){ req: Request =>

    val target = req.params("target")
    val format = req.params.getOrElse("format", "")
    val function = req.params("func")
    val to = req.params.getLongOrElse("to", new Date().getTime)
    val from = req.params.getLongOrElse("from", to - 86400 * 1000 )
    val token = req.params("token")

    val frs = if(function.equals("_empty")){
      callbackPool(QueryResult(Array(), Array()))
    }else{
      val tokenScope = tokenService.scope(token)

      val param = if(req.params.contains("func-param")){
        req.params.getAll("func-param").toSeq
      }else{
        Seq()
      }
      val queryScope = ScriptScope(tokenScope._1, from = from, to = to, tokenScope._3, companies = tokenScope._2.split(','))
      scriptQuery.execute(queryScope.toScope, function, param)
    }

    frs.map(rs =>{
      if(rs.isInstanceOf[Throwable]){
        throw rs.asInstanceOf[Throwable]
      }else{
        val isQueryResult: Boolean = rs.isInstanceOf[QueryResult]
        (target, isQueryResult) match {
          case ("json", true) =>
            response.ok.json(rs.asInstanceOf[QueryResult].format(format))
          case ("template", true) =>
            val f = if(format.length < 1){
              "label(label,value)"
            }else{
              format
            }
            response.ok.json(rs.asInstanceOf[QueryResult].format(f))
          case ("excel", true) =>
            val filename = s"$function-$from-$to.xlsx"
            response.ok.body(rs.asInstanceOf[QueryResult].format("excel()"))
              .contentType("binary/octet-stream")
              .header("Content-Disposition", s"""attachment; filename="$filename"""")
          case ("json", false) =>
            response.ok.body(rs.toString).contentType("application/json; charset=utf-8")
          case (_, false) =>
            response.ok.body(rs.toString)
          case _ =>
            response.badRequest.body(s"$target is not supported.")
        }
      }
    })
  }
}


































