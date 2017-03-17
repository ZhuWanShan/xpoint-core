package xpoint.api

import java.util.concurrent.TimeUnit

import com.twitter.util.{Await, CountDownLatch, Duration}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}
import xpoint.api.callback.ExecutorPool._

import scala.concurrent.blocking

/**
  * Created by szhu.spark@gmail.com on 16/03/2017.
  */
class FutureTest extends FlatSpec with Matchers with MockFactory{

  "Future" should "resolve" in {
    val doneSignal = new CountDownLatch(1)
    val rs = callbackPool{
      blocking{

        "Foo"
      }
    }

    new Thread(new Runnable {
      override def run(): Unit = {
        Thread.sleep(3000)
        doneSignal.countDown()
        doneSignal.countDown()
      }
    }).start()


    doneSignal.within(Duration(4, TimeUnit.SECONDS))

    print("Done")

   // print(Await.result(rs))



  }

}
