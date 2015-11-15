package comm

import hk.edu.polyu.ir.groupc.searchengine.Debug

/**
  * Created by beenotung on 11/15/15.
  */
object Test {
  def time[R](block: => R, repeat: Int = 1, report: Boolean = false, preGC: Boolean = false, postGC: Boolean = false): Array[(R, Long)] = {
    Array.tabulate(repeat)(i => {
      if (report)
        Debug.log("time " + (i + 1))
      if (preGC) {
        Debug.log("start preGC")
        Runtime.getRuntime.gc()
        Debug.log("finished preGC")
      }
      val r = time(block)
      if (postGC) {
        Debug.log("start postGC")
        Runtime.getRuntime.gc()
        Debug.log("finished postGC")
      }
      r
    })
  }

  def time[R](block: => R): (R, Long) = {
    val t0 = System.nanoTime()
    val result = block
    val t1 = System.nanoTime()
    (result, t1 - t0)
  }
}
