package xpoint.api.callback

import com.twitter.util.FuturePools

/**
  * Created by Spark Zhu<szhu.spark@gmail.com> on 06/12/2016.
  */
object ExecutorPool {
  val callbackPool = FuturePools.unboundedPool()
}
