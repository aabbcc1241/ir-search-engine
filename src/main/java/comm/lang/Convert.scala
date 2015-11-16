package comm.lang

import scala.collection.JavaConverters._

/**
  * Created by beenotung on 11/13/15.
  */
object Convert {
  def toJava[E](list: List[E]) = list.asJava
}
