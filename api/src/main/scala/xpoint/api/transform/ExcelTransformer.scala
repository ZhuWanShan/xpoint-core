package xpoint.api.transform
import java.io.ByteArrayOutputStream

import xpoint.engine.script.domain.QueryResult
import com.norbitltd.spoiwo.model.{Row, Sheet}
import com.norbitltd.spoiwo.natures.xlsx.Model2XlsxConversions._

/**
  * Created by szhu.spark@gmail.com on 07/03/2017.
  */
object ExcelTransformer extends Transformer{
  override def format(rs: QueryResult, param: String*): Any = {

    val name = if(param.nonEmpty){
      param(0)
    }else{
      "Sheet 1"
    }

    val buffer = new ByteArrayOutputStream()

    val head = rs.fields

    val data = rs.values.map(r => {
      Row().withCellValues(r: _*)
    })

    val sheet = Sheet(name = name,
      row = Row().withCellValues(head: _*),
      data: _*
    )
    sheet.convertAsXlsx().write(buffer)

    buffer.toByteArray
  }
}
