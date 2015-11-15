import java.io.File

import comm.Test
import hk.edu.polyu.ir.groupc.searchengine.Debug.log
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.TermInfoFactory

/**
  * Created by beenotung on 11/8/15.
  */
object PostLoadTest extends App {
  override def main(args: Array[String]) {
    log(this.getClass.getName)
    val path = "/home/beenotung/Documents/polyu/IR/group assignment/res/post1.txt"
    val path_out = "res/term_index.txt"
    def block = {
      TermInfoFactory.load(new File(path_out))
      TermInfoFactory.getTermIndex.reset
    }
    val rs = Test.time(block, 10, report = true, preGC = true, postGC = true)
    rs.foreach(r => log("loading used " + r._2 + " ns"))
  }
}
