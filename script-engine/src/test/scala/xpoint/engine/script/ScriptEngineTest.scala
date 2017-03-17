package xpoint.engine.script

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}
import xpoint.engine.script.domain.QueryResult
import xpoint.engine.script.service.SqlService

/**
  * Created by szhu.spark@gmail.com on 13/03/2017.
  */
class ScriptEngineTest extends FlatSpec with Matchers with MockFactory{
  val sqlQuery: SqlService = mock[SqlService]

  val scriptQuery: ScriptEngine = new ScriptEngine {
    override val sql: SqlService = sqlQuery
  }

  val queryResult = QueryResult(
    Array("A", "B"),
    Array(
      Array("X", 1), Array("Y", 2)
    )
  )

  "Script Query" should "execute script" in {
/*
    (scriptLoader.load(_ : String)).expects(*).returning(
      """
        |function foo(ctx, x){
        |   //var lodash = _
        |   //Log.d("111")
        |   print(_.map)
        |   //Log.d(SQL)
        |   print(SQL)
        |   print(ctx)
        |   print(x);
        |   return SQL.query(x);
        |}
      """.stripMargin
    ).twice()

    (scriptLoader.location: () => String ).expects().returning("/ws/data/xpoint-script-source/function").once()

    (sqlQuery.query(_: String)).expects(*).returning(queryResult).twice()

    val rs = scriptQuery.execute("bar", "foo", params = Seq("World"))
    scriptQuery.cleanContext()
    val rs2 = scriptQuery.execute("bar", "foo", params = Seq("World"))

    assertResult(rs.fields){
      Array("A", "B")
    }*/

  }

}
