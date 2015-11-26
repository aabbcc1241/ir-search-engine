package comm.lang

import java.util.function.Consumer
import javafx.util.Callback

import scala.collection.JavaConverters._
import scala.language.implicitConversions

/**
  * Created by beenotung on 11/13/15.
  */
object Convert {
  def toJava[E](list: List[E]) = list.asJava

  implicit def funcToRunnable(func: () => Unit): Runnable = new Runnable() {
    override def run() = func()
  }

  implicit def funcToCallback[S, T](func: S => T): Callback[S, T] = new Callback[S, T] {
    override def call(p: S): T = func(p)
  }

  implicit def funcToConsumer[T](func: T => Unit): Consumer[T] = new Consumer[T] {
    override def accept(t: T): Unit = func(t)
  }

  def Consumer[T](func: T => Unit): Consumer[T] = {
    new Consumer[T] {
      override def accept(t: T): Unit = func(t)
    }
  }
  object Empty{
    def consumer[T]=new Consumer[T] {
      override def accept(t: T): Unit = {}
    }
  }
}
