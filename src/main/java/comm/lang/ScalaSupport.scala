package comm.lang

import java.util.function.Consumer

import scala.collection.mutable
import scala.collection.parallel.mutable.ParArray

/**
  * Created by beenotung on 11/13/15.
  */
object ScalaSupport {
  def foreachMap[K, V](hashMap: mutable.HashMap[K, V], consumer: Consumer[(K, V)]) = {
    hashMap.foreach(e => consumer.accept(e))
  }
}
