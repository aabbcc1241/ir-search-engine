package comm.lang

import java.util.function.Consumer

import scala.collection.parallel.ParMap

/**
  * Created by beenotung on 11/13/15.
  */
object ScalaSupport {
  def function1[T, R](func: java.util.function.Function[T, R]) = {
    (t: T) => func.apply(t)
  }

  def foreachParMap[K, V](map: ParMap[K, V], consumer: Consumer[(K, V)]) = {
    map.foreach(e => consumer.accept(e))
  }

  def foreachMap[K, V](map: scala.collection.mutable.HashMap[K, V], consumer: Consumer[(K, V)]) = {
    map.foreach(e => consumer.accept(e))
  }

  def foreachMap[K, V](map: Map[K, V], consumer: Consumer[(K, V)]) = {
    map.foreach(e => consumer.accept(e))
  }
}
