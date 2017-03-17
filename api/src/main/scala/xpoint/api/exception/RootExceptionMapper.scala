package xpoint.api.exception

import java.io.{PrintWriter, StringWriter}
import javax.inject.Inject

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.inject.Logging

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 06/12/2016.
  */
class RootExceptionMapper @Inject()(response: ResponseBuilder) extends ExceptionMapper[Exception] with Logging {
  override def toResponse(request: Request, e: Exception): Response = {

    val stackBuffer = new StringWriter()
    val printer = new PrintWriter(stackBuffer)

    e.printStackTrace(printer)
    printer.flush()
    val stack = stackBuffer.getBuffer.toString
    printer.close()

    response.status(500).html(s"<pre>$stack</pre>")
  }
}
