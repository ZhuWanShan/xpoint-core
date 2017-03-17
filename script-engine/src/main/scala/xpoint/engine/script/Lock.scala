package xpoint.engine.script

import java.util.concurrent.TimeUnit

import com.twitter.util.{CountDownLatch, Duration}

/**
  * Created by szhu.spark@gmail.com on 16/03/2017.
  */
class Lock() {
  val countDownLatch: CountDownLatch = new CountDownLatch(1)

  def lock(t: Long): Unit = {
    countDownLatch.within(Duration(t, TimeUnit.SECONDS))
  }

  def lock(): Unit ={
    lock(120)
  }

  def unlock(): Unit ={
    countDownLatch.countDown()
  }
}
