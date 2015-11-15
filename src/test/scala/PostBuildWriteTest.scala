import java.io.File

import hk.edu.polyu.ir.groupc.searchengine.Debug.log
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.TermInfoFactory

/**
  * Created by beenotung on 11/8/15.
  */
object PostBuildWriteTest extends App {
  override def main(args: Array[String]) {
    log(this.getClass.getName)
    val path = "/home/beenotung/Documents/polyu/IR/group assignment/res/post1.txt"
    val path_out = "res/term_index.txt"
    TermInfoFactory.build(new File(path))
    TermInfoFactory.getTermIndex.writeToFile(path_out)
  }
}
