package xpoint.engine.script

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 30/11/2016.
  */
case class ScriptScope(
                        credential: String,
                        from: Long = -1,
                        to: Long = -1,
                        lang: String = "en",
                        companies: Array[String] = Array()
                      ) {

  def toScope: Scope = {
    new Scope(credential, from, to, lang, companies)
  }
}