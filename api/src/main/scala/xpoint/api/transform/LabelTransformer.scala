package xpoint.api.transform
import xpoint.engine.script.domain.QueryResult

/**
  * Created by szhu.spark@gmail.com on 06/03/2017.
  */
object LabelTransformer extends  Transformer{
  override def format(rs: QueryResult, param: String*): Any = {
    if(param.length < 2){
      throw new IllegalArgumentException("Label Transformer need two param, label and value")
    }
    val label = param(0)
    val value = param(1)

    val labelIndex = rs.fields.indexOf(label)
    val valueIndex = rs.fields.indexOf(value)

    if(labelIndex < 0 || valueIndex < 0){
      throw new IllegalArgumentException(s"Cannot find label=$label, and value=$value in ${rs.fields}")
    }

    rs.values.map(it=> {
      Seq(it(labelIndex), it(valueIndex))
    }).toSeq
  }
}
