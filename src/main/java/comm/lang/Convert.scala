package comm.lang

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
}
