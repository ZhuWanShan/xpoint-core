package xpoint.api.transform

/**
  * Created by szhu.spark@gmail.com on 06/03/2017.
  */
object Transforms {
  val transforms = Map(
    "label" -> LabelTransformer,
    "table" -> TableTransformer,
    "excel" -> ExcelTransformer
  )
  def get(name: String): Transformer = {
    transforms(name)
  }

  def default: Transformer = {
    transforms("table")
  }
}
