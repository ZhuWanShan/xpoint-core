package xpoint.api.transform

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 06/12/2016.
  */
case class Table(columns: Array[TextColumn], rows: Seq[Array[Any]], `type`: String =  "table")
case class TextColumn(text: String)