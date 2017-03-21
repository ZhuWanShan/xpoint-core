package xpoint.engine.script.service

import java.util.UUID
import java.util.concurrent.TimeUnit

import com.twitter.util._

/**
  * Created by szhu.spark@gmail.com on 21/03/2017.
  */
trait ScriptHook {

  private val pool = new java.util.concurrent.ConcurrentHashMap[String, Any]()

  val callbackPool: FuturePool

  def hook(): String = {
    val id = UUID.randomUUID().toString
    pool.put(id, new CountDownLatch(1))
    id
  }

  def put(key: String, value: Any): Unit = {
    if(pool.containsKey(key)){
      val hook = pool.get(key)
      if(hook.isInstanceOf[CountDownLatch]){
        pool.put(key, value)
        hook.asInstanceOf[CountDownLatch].countDown()
      }
    }
  }

  def result(key: String): Future[Any] = {
    if(pool.containsKey(key)){
      callbackPool{
        val hook = pool.get(key)
        if(hook.isInstanceOf[CountDownLatch]){
          hook.asInstanceOf[CountDownLatch].within(Duration(120, TimeUnit.SECONDS))
          pool.remove(key)
        }else{
          pool.remove(key)
        }
      }
    }else{
      callbackPool({
        null
      })
    }
  }
}
